package com.codependent.outboxpattern.transfer.service

import com.codependent.outboxpattern.account.TransferEmitted

interface FraudDetectionService {

    fun isFraudulent(transfer: TransferEmitted): Boolean

}
