package com.codependent.outboxpattern.operations.stream

import org.apache.kafka.streams.kstream.KStream
import org.springframework.cloud.stream.annotation.Input
import org.springframework.cloud.stream.annotation.Output

interface TransferKafkaStreamsProcessor {

    @Input("input")
    fun input(): KStream<*, *>

    @Output("outputOk")
    fun outputOk(): KStream<*, *>

    @Output("outputKo")
    fun outputKo(): KStream<*, *>

}
