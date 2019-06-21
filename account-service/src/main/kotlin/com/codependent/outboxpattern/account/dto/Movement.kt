package com.codependent.outboxpattern.account.dto

import java.util.*

data class Movement(val id: Int,
                    val transactionId: String,
                    val type: MovementType,
                    val relatedAccountId: Long,
                    val ammount: Float,
                    val date: Date)
