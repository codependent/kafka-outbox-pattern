package com.codependent.outboxpattern.operations.consumer

import com.codependent.outboxpattern.account.TransferEmitted
import com.codependent.outboxpattern.operations.entity.Transfer
import com.codependent.outboxpattern.operations.service.TransferService
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.messaging.Sink
import org.springframework.stereotype.Component

@Component
class TransferListener(private val transferService: TransferService) {

    @StreamListener(Sink.INPUT)
    fun handle(transfer: TransferEmitted) {
        if (transferService.getByTransferId(transfer.getTransferId()) == null) {
            transferService.save(Transfer(0,
                    transfer.getTransferId(),
                    transfer.getSourceAccountId(),
                    transfer.getDestinationAccountId(),
                    transfer.getAmmount()))
        }
    }

}
