package com.codependent.outboxpattern.account.service

import com.codependent.outboxpattern.account.entity.Account
import com.codependent.outboxpattern.account.dto.Transfer

interface AccountService {

    fun save(account: Account)
    fun getAll(): List<Account>
    fun transfer(transfer: Transfer)
    fun receiveTransfer(accountId: Long, ammount: Float)
}
