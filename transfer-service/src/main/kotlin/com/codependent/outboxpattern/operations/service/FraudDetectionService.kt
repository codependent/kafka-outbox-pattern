package com.codependent.outboxpattern.operations.service

import com.codependent.outboxpattern.account.TransferEmitted

interface FraudDetectionService {

    fun isFraudulent(transfer: TransferEmitted): Boolean

}
