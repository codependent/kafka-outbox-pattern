package com.codependent.outboxpattern.account.consumer

import org.springframework.cloud.stream.annotation.Input
import org.springframework.messaging.SubscribableChannel

interface Sinks {

    @Input("input")
    fun input(): SubscribableChannel

    @Input("inputFraudulent")
    fun inputFraudulent(): SubscribableChannel
}
