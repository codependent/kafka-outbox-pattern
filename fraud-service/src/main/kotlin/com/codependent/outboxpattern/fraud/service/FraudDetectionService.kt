package com.codependent.outboxpattern.fraud.service

import com.codependent.outboxpattern.account.TransferEmitted

interface FraudDetectionService {

    fun isFraudulent(transfer: TransferEmitted): Boolean

}
