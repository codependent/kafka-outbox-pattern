package com.codependent.outboxpattern.outbox.service

import com.codependent.outboxpattern.outbox.entity.Outbox

interface OutboxService {

    fun getAll(): List<Outbox>

    fun getPending(): List<Outbox>

    fun save(topic: String, entity: Any)

    fun markAsProcessed(outboxId: Int)
}
