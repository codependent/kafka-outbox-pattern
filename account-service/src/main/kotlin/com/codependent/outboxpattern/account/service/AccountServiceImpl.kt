package com.codependent.outboxpattern.account.service

import com.codependent.outboxpattern.account.TransferApproved
import com.codependent.outboxpattern.account.TransferEmitted
import com.codependent.outboxpattern.account.dto.Account
import com.codependent.outboxpattern.account.dto.MovementType
import com.codependent.outboxpattern.account.dto.Transfer
import com.codependent.outboxpattern.account.entity.AccountEntity
import com.codependent.outboxpattern.account.entity.MovementEntity
import com.codependent.outboxpattern.account.exception.AccountDoesntExistException
import com.codependent.outboxpattern.account.exception.FundsNotAvailableException
import com.codependent.outboxpattern.account.mapper.ObjectMapper
import com.codependent.outboxpattern.account.repository.AccountRepository
import com.codependent.outboxpattern.outbox.service.OutboxService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.*

@Transactional
@Service
class AccountServiceImpl(private val accountRepository: AccountRepository,
                         private val outboxService: OutboxService,
                         private val mapper: ObjectMapper) : AccountService {

    //TODO Deduplicate
    override fun receiveTransfer(transfer: TransferApproved) {
        val destinationAccount = accountRepository.findById(transfer.getDestinationAccountId())
        destinationAccount.ifPresent {
            val funds = BigDecimal(it.funds.toString())
            val transferAmmount = BigDecimal(transfer.getAmmount().toString())
            it.funds = funds.add(transferAmmount).toFloat()

            val movementEntity = MovementEntity(0, transfer.getTransferId(), MovementType.PAYMENT,
                    it, transfer.getSourceAccountId(), transfer.getAmmount(), Date())
            it.movements.add(movementEntity)

            accountRepository.save(it)
        }
    }

    //TODO Deduplicate
    override fun cancelTransfer(transfer: TransferEmitted) {
        val sourceAccount = accountRepository.findById(transfer.getSourceAccountId())
        sourceAccount.ifPresent { sAccount ->
            val funds = BigDecimal(sAccount.funds.toString())
            val transferAmmount = BigDecimal(transfer.getAmmount().toString())
            sAccount.funds = funds.add(transferAmmount).toFloat()
            sAccount.movements.removeIf { it.transactionId == transfer.getTransferId() }
            accountRepository.save(sAccount)
        }
    }

    override fun getAll(): List<Account> {
        return mapper.map(accountRepository.findAll(), Account::class.java)
    }

    override fun save(account: Account) {
        accountRepository.save(mapper.map(account, AccountEntity::class.java))
    }

    override fun transfer(transfer: Transfer) {
        val account = accountRepository.findById(transfer.sourceAccountId)
        when (account.isPresent) {
            true -> {
                val sourceAccount = account.get()
                if (sourceAccount.funds >= transfer.ammount) {
                    val funds = BigDecimal(sourceAccount.funds.toString())
                    val transferAmmount = BigDecimal(transfer.ammount.toString())
                    sourceAccount.funds = funds.subtract(transferAmmount).toFloat()

                    val movement = MovementEntity(0, UUID.randomUUID().toString(),
                            MovementType.CHARGE, sourceAccount, transfer.destinationAccountId, transfer.ammount, Date())
                    sourceAccount.movements.add(movement)

                    accountRepository.save(sourceAccount)

                    outboxService.save(movement.transactionId, "transfer",
                            TransferEmitted(movement.transactionId,
                                    transfer.sourceAccountId,
                                    transfer.destinationAccountId,
                                    transfer.ammount))
                } else {
                    throw FundsNotAvailableException(transfer.sourceAccountId)
                }
            }
            false -> throw AccountDoesntExistException(transfer.sourceAccountId)
        }
    }
}
