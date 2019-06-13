package com.codependent.outboxpattern.outbox.service

import com.codependent.outboxpattern.outbox.entity.Outbox
import com.codependent.outboxpattern.outbox.entity.State
import com.codependent.outboxpattern.outbox.repository.OutboxRepository
import org.apache.avro.specific.SpecificRecordBase
import org.springframework.cloud.stream.schema.avro.AvroSchemaRegistryClientMessageConverter
import org.springframework.messaging.Message
import org.springframework.messaging.MessageHeaders
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class OutboxServiceImpl(private val outboxRepository: OutboxRepository,
                        private val avroMessageConverter: AvroSchemaRegistryClientMessageConverter) : OutboxService {

    override fun save(messageId: String, topic: String, entity: SpecificRecordBase) {
        val message = avroMessageConverter.toMessage(entity, MessageHeaders(mutableMapOf(MessageHeaders.CONTENT_TYPE to "application/*+avro" as Any))) as Message<*>
        outboxRepository.save(Outbox(0, messageId, topic, entity.schema.name, State.PENDING, message.payload as ByteArray))
    }

}
