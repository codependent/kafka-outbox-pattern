package com.codependent.outboxpattern.operations.service

import com.codependent.outboxpattern.operations.entity.Transfer

interface TransferService {

    fun save(transfer: Transfer)
    fun getAll(): List<Transfer>
    fun get(id: Int): Transfer?
    fun getByTransferId(transferId: String): Transfer?

}
