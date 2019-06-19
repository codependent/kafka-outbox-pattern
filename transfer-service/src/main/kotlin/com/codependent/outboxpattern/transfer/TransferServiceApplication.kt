package com.codependent.outboxpattern.transfer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TransferServiceApplication

fun main(args: Array<String>) {
    runApplication<TransferServiceApplication>(*args)
}
