package com.codependent.outboxpattern.fraud.service

import com.codependent.outboxpattern.account.TransferEmitted
import org.springframework.stereotype.Service

@Service
class FraudDetectionServiceImpl : FraudDetectionService {

    override fun isFraudulent(transfer: TransferEmitted): Boolean {
        return fraudulentDestinations.contains(transfer.destinationAccountId)
    }

    private val fraudulentDestinations = setOf<Long>(900, 1000, 2000, 3000)

}
