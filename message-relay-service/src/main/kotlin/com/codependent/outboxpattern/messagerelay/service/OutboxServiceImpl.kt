package com.codependent.outboxpattern.messagerelay.service

import com.codependent.outboxpattern.messagerelay.entity.Outbox
import com.codependent.outboxpattern.messagerelay.entity.State
import com.codependent.outboxpattern.messagerelay.repository.OutboxRepository
import org.springframework.cloud.stream.schema.avro.AvroSchemaRegistryClientMessageConverter
import org.springframework.messaging.Message
import org.springframework.messaging.MessageHeaders
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class OutboxServiceImpl(private val outboxRepository: OutboxRepository) : OutboxService {

    override fun markAsProcessed(outboxId: Int) {
        val outbox = outboxRepository.findById(outboxId)
        outbox.ifPresent {
            it.state = State.PROCESSED
            outboxRepository.save(it)
        }

    }

    override fun getPending(): List<Outbox> {
        return outboxRepository.findByState(State.PENDING)
    }

    override fun getAll(): List<Outbox> {
        return outboxRepository.findAll()
    }
}
