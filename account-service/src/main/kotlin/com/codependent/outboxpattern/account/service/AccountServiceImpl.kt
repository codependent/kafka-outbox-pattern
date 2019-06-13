package com.codependent.outboxpattern.account.service

import com.codependent.outboxpattern.account.TransferEmmitted
import com.codependent.outboxpattern.account.dto.Transfer
import com.codependent.outboxpattern.account.entity.Account
import com.codependent.outboxpattern.account.exception.AccountDoesntExistException
import com.codependent.outboxpattern.account.exception.FundsNotAvailableException
import com.codependent.outboxpattern.account.repository.AccountRepository
import com.codependent.outboxpattern.outbox.service.OutboxService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Transactional
@Service
class AccountServiceImpl(private val accountRepository: AccountRepository,
                         private val outboxService: OutboxService) : AccountService {

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
                    sourceAccount.funds -= transfer.ammount
                    accountRepository.save(sourceAccount)
                    val transferId = UUID.randomUUID().toString()
                    outboxService.save(transferId, "transfer",
                            TransferEmmitted(transferId,
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
