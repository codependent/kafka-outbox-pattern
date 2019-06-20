package com.codependent.outboxpattern.account.consumer

import com.codependent.outboxpattern.account.TransferApproved
import com.codependent.outboxpattern.account.TransferEmitted
import com.codependent.outboxpattern.account.service.AccountService
import org.slf4j.LoggerFactory
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.messaging.Sink
import org.springframework.stereotype.Component

@Component
class TransferReceivedListener(private val accountService: AccountService) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @StreamListener(Sink.INPUT)
    fun handle(transfer: TransferApproved) {
        logger.info("****** Processing approved transfer {}", transfer)
        accountService.receiveTransfer(transfer.getDestinationAccountId(), transfer.getAmmount())
    }

}
