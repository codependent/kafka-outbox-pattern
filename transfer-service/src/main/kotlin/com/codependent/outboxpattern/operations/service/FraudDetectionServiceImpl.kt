package com.codependent.outboxpattern.operations.service

import com.codependent.outboxpattern.account.TransferEmitted
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class FraudDetectionServiceImpl : FraudDetectionService {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun isFraudulent(transfer: TransferEmitted): Boolean {
        val isFraudulentDestination = fraudulentDestinations.contains(transfer.getDestinationAccountId())
        logger.info("****** Is {} a fraudulent transfer? -> {}", transfer, isFraudulentDestination)
        return isFraudulentDestination
    }

    private val fraudulentDestinations = setOf<Long>(900, 1000, 2000, 3000)

}
