package com.codependent.outboxpattern.fraud.configuration

import com.codependent.outboxpattern.account.TransferEmitted
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.KStream
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.Input
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.binder.kafka.streams.annotations.KafkaStreamsProcessor
import org.springframework.messaging.handler.annotation.SendTo


@EnableBinding(KafkaStreamsProcessor::class)
class KafkaStreamsConfiguration(/*private val streamsBuilder: StreamsBuilder*/) {

    @StreamListener
    @SendTo("output")
    fun process(@Input("input") input: KStream<String, TransferEmitted>): KStream<String, TransferEmitted> {

        /*val dedupStoreBuilder = Stores.windowStoreBuilder(
                Stores.persistentWindowStore(storeName,
                        retentionPeriod,
                        windowSize,
                        false
                ),
                Serdes.String(),
                Serdes.Long())

        builder.addStateStore(dedupStoreBuilder)
*/
        return input

    }

}
