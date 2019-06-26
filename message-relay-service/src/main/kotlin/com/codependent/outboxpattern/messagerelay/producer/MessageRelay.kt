package com.codependent.outboxpattern.messagerelay.producer

import com.codependent.outboxpattern.messagerelay.service.OutboxService
import org.springframework.cloud.stream.binding.BinderAwareChannelResolver
import org.springframework.cloud.stream.messaging.Source
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.MessageHeaders
import org.springframework.messaging.support.MessageBuilder
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class MessageRelay(private val outboxService: OutboxService,
                   private val resolver: BinderAwareChannelResolver,
                   private val source: Source) {

    @Scheduled(fixedDelay = 10000)
    fun checkOutbox() {
        val pending = outboxService.getPending()
        pending.forEach {
            val message = MessageBuilder.withPayload(it.payload)
                    .setHeader(KafkaHeaders.MESSAGE_KEY, it.messageKey)
                    .setHeader(MessageHeaders.CONTENT_TYPE, it.contentType)
                    .build()
            //resolver.resolveDestination(it.topic).send(message)
            source.output().send(message)
            outboxService.markAsProcessed(it.id)
        }
    }

}
