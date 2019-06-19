package com.codependent.outboxpattern.operations.entity

import javax.persistence.*

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["transferId"])])
data class Transfer(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Int,
                    val transferId: String,
                    val sourceAccountId: Long,
                    val destinationAccountId: Long,
                    val ammount: Float)
