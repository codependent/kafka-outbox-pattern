package com.codependent.outboxpattern.account.service

import com.codependent.outboxpattern.account.TransferApproved
import com.codependent.outboxpattern.account.TransferEmitted
import com.codependent.outboxpattern.account.dto.Account
import com.codependent.outboxpattern.account.dto.Transfer

interface AccountService {

    fun save(account: Account)
    fun getAll(): List<Account>
    fun transfer(transfer: Transfer)
    fun receiveTransfer(transfer: TransferApproved)
    fun cancelTransfer(transfer: TransferEmitted)
}
