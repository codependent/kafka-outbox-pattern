package com.codependent.outboxpattern.outbox.relay

import com.codependent.outboxpattern.outbox.service.OutboxService
import org.springframework.cloud.stream.messaging.Source
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.support.MessageBuilder
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class MessageRelay(private val outboxService: OutboxService,
                   private val customerKafkaProducer: Source) {

    @Scheduled(fixedDelay = 10000)
    fun checkOutbox() {
        val pending = outboxService.getPending()
        pending.forEach {
            val payloadByteArray = it.payload.toByteArray()
            val message = MessageBuilder.withPayload(payloadByteArray).setHeader(KafkaHeaders.MESSAGE_KEY, it.messageKey).build()
            customerKafkaProducer.output().send(message)
            outboxService.markAsProcessed(it.id)
        }
    }

}
