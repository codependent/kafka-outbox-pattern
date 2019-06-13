package com.codependent.outboxpattern.transfer.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Transfer(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Int,
                    val transferId: String,
                    val sourceAccountId: Long,
                    val destinationAccountId: Long,
                    val ammount: Float)
