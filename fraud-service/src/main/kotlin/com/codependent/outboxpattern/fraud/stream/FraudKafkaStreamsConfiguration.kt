package com.codependent.outboxpattern.fraud.stream

import com.codependent.outboxpattern.account.TransferEmitted
import com.codependent.outboxpattern.fraud.service.FraudDetectionService
import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.streams.kstream.TransformerSupplier
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.Input
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.binder.kafka.streams.annotations.KafkaStreamsProcessor
import org.springframework.cloud.stream.binder.kafka.streams.annotations.KafkaStreamsStateStore
import org.springframework.cloud.stream.binder.kafka.streams.properties.KafkaStreamsStateStoreProperties
import org.springframework.messaging.handler.annotation.SendTo

const val DEDUP_STORE = "dedup-store"

@EnableBinding(KafkaStreamsProcessor::class)
class FraudKafkaStreamsConfiguration(private val fraudDetectionService: FraudDetectionService) {

    @KafkaStreamsStateStore(name = DEDUP_STORE, type = KafkaStreamsStateStoreProperties.StoreType.KEYVALUE)
    @StreamListener
    @SendTo("output")
    fun process(@Input("input") input: KStream<String, TransferEmitted>): KStream<String, TransferEmitted> {
        return input
                .transform(TransformerSupplier { DeduplicationTransformer() }, DEDUP_STORE)
                .filter { _, value -> fraudDetectionService.isFraudulent(value) }

    }

}
