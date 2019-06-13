package com.codependent.outboxpattern.transfer.service

import com.codependent.outboxpattern.transfer.entity.Transfer

interface TransferService {

    fun save(transfer: Transfer)
    fun getAll(): List<Transfer>
    fun get(id: Int): Transfer?

}
