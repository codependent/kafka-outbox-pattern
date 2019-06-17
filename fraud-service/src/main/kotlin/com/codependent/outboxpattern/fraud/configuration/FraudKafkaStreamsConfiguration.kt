package com.codependent.outboxpattern.fraud.configuration

import com.codependent.outboxpattern.account.TransferEmitted
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig
import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.streams.kstream.TransformerSupplier
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.Input
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.binder.kafka.streams.annotations.KafkaStreamsStateStore
import org.springframework.cloud.stream.binder.kafka.streams.properties.KafkaStreamsStateStoreProperties
import org.springframework.messaging.handler.annotation.SendTo

const val DEDUP_STORE = "dedup-store"

@EnableBinding(FraudKafkaStreamsProcessor::class)
class FraudKafkaStreamsConfiguration {

    @KafkaStreamsStateStore(name = DEDUP_STORE, type = KafkaStreamsStateStoreProperties.StoreType.KEYVALUE)
    @StreamListener
    @SendTo("output")
    fun process(@Input("input") input: KStream<String, TransferEmitted>): KStream<String, TransferEmitted> {

        val serdeConfig = mapOf(
                AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG to "http://localhost:8081")

        return input
                .filter { key, value -> true }
                .transform(TransformerSupplier { DeduplicationTransformer() }, DEDUP_STORE)

    }

}
