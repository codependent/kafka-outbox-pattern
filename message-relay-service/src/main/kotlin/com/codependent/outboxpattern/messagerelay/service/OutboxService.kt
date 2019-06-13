package com.codependent.outboxpattern.messagerelay.service

import com.codependent.outboxpattern.messagerelay.entity.Outbox

interface OutboxService {

    fun getAll(): List<Outbox>

    fun getPending(): List<Outbox>

    fun markAsProcessed(outboxId: Int)
}
