package com.codependent.outboxpattern.account.service

import com.codependent.outboxpattern.account.TransferEmitted
import com.codependent.outboxpattern.account.dto.Transfer
import com.codependent.outboxpattern.account.entity.Account
import com.codependent.outboxpattern.account.exception.AccountDoesntExistException
import com.codependent.outboxpattern.account.exception.FundsNotAvailableException
import com.codependent.outboxpattern.account.repository.AccountRepository
import com.codependent.outboxpattern.outbox.service.OutboxService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.*

@Transactional
@Service
class AccountServiceImpl(private val accountRepository: AccountRepository,
                         private val outboxService: OutboxService) : AccountService {

    override fun receiveTransfer(accountId: Long, ammount: Float) {
        val destinationAccount = accountRepository.findById(accountId)
        destinationAccount.ifPresent {
            val funds = BigDecimal(it.funds.toString())
            val transferAmmount = BigDecimal(ammount.toString())
            it.funds = funds.add(transferAmmount).toFloat()
            accountRepository.save(it)
        }
    }

    override fun cancelTransfer(accountId: Long, ammount: Float) {
        val sourceAccount = accountRepository.findById(accountId)
        sourceAccount.ifPresent {
            val funds = BigDecimal(it.funds.toString())
            val transferAmmount = BigDecimal(ammount.toString())
            it.funds = funds.add(transferAmmount).toFloat()
            accountRepository.save(it)
        }
    }

    override fun getAll(): List<Account> {
        return accountRepository.findAll()
    }

    override fun save(account: Account) {
        accountRepository.save(account)
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
                    accountRepository.save(sourceAccount)
                    val transferId = UUID.randomUUID().toString()
                    outboxService.save(transferId, "transfer",
                            TransferEmitted(transferId,
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
