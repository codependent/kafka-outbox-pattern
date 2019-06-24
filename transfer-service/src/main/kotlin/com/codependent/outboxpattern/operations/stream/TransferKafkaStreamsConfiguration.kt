package com.codependent.outboxpattern.operations.stream

import com.codependent.outboxpattern.account.TransferApproved
import com.codependent.outboxpattern.account.TransferEmitted
import com.codependent.outboxpattern.operations.service.FraudDetectionService
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.streams.kstream.Predicate
import org.apache.kafka.streams.kstream.TransformerSupplier
import org.slf4j.LoggerFactory
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.Input
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.binder.kafka.streams.annotations.KafkaStreamsStateStore
import org.springframework.cloud.stream.binder.kafka.streams.properties.KafkaStreamsStateStoreProperties
import org.springframework.messaging.handler.annotation.SendTo

const val DEDUP_STORE = "dedup-store"

@Suppress("UNCHECKED_CAST")
@EnableBinding(TransferKafkaStreamsProcessor::class)
class FraudKafkaStreamsConfiguration(private val fraudDetectionService: FraudDetectionService) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @KafkaStreamsStateStore(name = DEDUP_STORE, type = KafkaStreamsStateStoreProperties.StoreType.KEYVALUE)
    @StreamListener
    @SendTo(value = ["outputKo", "outputOk"])
    fun process(@Input("input") input: KStream<String, TransferEmitted>): Array<KStream<String, *>>? {
        val fork: Array<KStream<String, *>> = input
                .transform(TransformerSupplier { DeduplicationTransformer() }, DEDUP_STORE)
                .branch(Predicate { _: String, value -> fraudDetectionService.isFraudulent(value) },
                        Predicate { _: String, value -> !fraudDetectionService.isFraudulent(value) }) as Array<KStream<String, *>>

        fork[1] = fork[1].map { key, value ->
            val transferApproved = TransferApproved((value as TransferEmitted).getTransferId(), value.getSourceAccountId(),
                    value.getDestinationAccountId(), value.getAmmount())
            logger.info("****** Sending TransferApproved event {} to account topic", transferApproved)
            KeyValue(key, transferApproved)
        }
        return fork
    }

}
