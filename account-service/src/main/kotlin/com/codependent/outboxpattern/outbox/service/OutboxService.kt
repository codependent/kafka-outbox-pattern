package com.codependent.outboxpattern.outbox.service

import org.apache.avro.specific.SpecificRecordBase

interface OutboxService {

    fun save(messageId: String, topic: String, entity: SpecificRecordBase)

}
