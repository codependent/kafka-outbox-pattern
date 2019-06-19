package com.codependent.outboxpattern.transfer.stream

import com.codependent.outboxpattern.account.TransferApproved
import com.codependent.outboxpattern.account.TransferEmitted
import com.codependent.outboxpattern.transfer.service.FraudDetectionService
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.streams.kstream.Predicate
import org.apache.kafka.streams.kstream.Produced
import org.apache.kafka.streams.kstream.TransformerSupplier
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.Input
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.binder.kafka.streams.annotations.KafkaStreamsStateStore
import org.springframework.cloud.stream.binder.kafka.streams.properties.KafkaStreamsStateStoreProperties

const val DEDUP_STORE = "dedup-store"

@EnableBinding(TransferKafkaStreamsSink::class)
class FraudKafkaStreamsConfiguration(private val fraudDetectionService: FraudDetectionService) {

    @KafkaStreamsStateStore(name = DEDUP_STORE, type = KafkaStreamsStateStoreProperties.StoreType.KEYVALUE)
    @StreamListener
    fun process(@Input("input") input: KStream<String, TransferEmitted>) {

        val stringSerde = Serdes.StringSerde()

        val serdeConfig = mapOf(
                AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG to "http://localhost:8081")


        val transferEmittedSerde = SpecificAvroSerde<TransferEmitted>()
        transferEmittedSerde.configure(serdeConfig, false)

        val transferApprovedSerde = SpecificAvroSerde<TransferApproved>()
        transferApprovedSerde.configure(serdeConfig, false)

        val fork = input.transform(TransformerSupplier { DeduplicationTransformer() }, DEDUP_STORE)
                .branch(Predicate { _, value -> fraudDetectionService.isFraudulent(value) },
                        Predicate { _, value -> !fraudDetectionService.isFraudulent(value) })
        fork[0].to("fraudulent-transfer", Produced.with(stringSerde, transferEmittedSerde))
        fork[1]
                .mapValues { tee -> TransferApproved(tee.getTransferId(), tee.getSourceAccountId(), tee.getDestinationAccountId(), tee.getAmmount()) }
                .to("account", Produced.with(stringSerde, transferApprovedSerde))
    }

}
