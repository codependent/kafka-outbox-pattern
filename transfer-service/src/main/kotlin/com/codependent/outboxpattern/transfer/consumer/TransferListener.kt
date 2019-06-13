package com.codependent.outboxpattern.transfer.consumer

import com.codependent.outboxpattern.account.TransferEmmitted
import com.codependent.outboxpattern.transfer.entity.Transfer
import com.codependent.outboxpattern.transfer.service.TransferService
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.messaging.Sink
import org.springframework.stereotype.Component

@Component
class TransferListener(private val transferService: TransferService) {

    @StreamListener(Sink.INPUT)
    fun handle(transfer: TransferEmmitted) {
        transferService.save(Transfer(0,
                transfer.getTransferId(),
                transfer.getSourceAccountId(),
                transfer.getDestinationAccountId(),
                transfer.getAmmount()))
    }

}
