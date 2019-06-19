package com.codependent.outboxpattern

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Sink
import org.springframework.cloud.stream.messaging.Source
import org.springframework.scheduling.annotation.EnableScheduling

@EnableBinding(Sink::class)
@SpringBootApplication
class OperationsServiceApplication

fun main(args: Array<String>) {
    runApplication<OperationsServiceApplication>(*args)
}
