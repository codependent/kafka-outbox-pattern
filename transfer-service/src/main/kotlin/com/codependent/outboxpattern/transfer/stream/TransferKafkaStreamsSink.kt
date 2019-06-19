package com.codependent.outboxpattern.transfer.stream

import org.apache.kafka.streams.kstream.KStream
import org.springframework.cloud.stream.annotation.Input

interface TransferKafkaStreamsSink {

    @Input("input")
    fun input(): KStream<*, *>

}
