package com.codependent.outboxpattern.account.consumer

import com.codependent.outboxpattern.account.TransferApproved
import com.codependent.outboxpattern.account.TransferEmitted
import com.codependent.outboxpattern.account.service.AccountService
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.messaging.Sink
import org.springframework.stereotype.Component

@Component
class TransferReceivedListener(private val accountService: AccountService) {

    @StreamListener(Sink.INPUT)
    fun handle(transfer: TransferApproved) {
        accountService.receiveTransfer(transfer.getDestinationAccountId(), transfer.getAmmount())
    }

}
