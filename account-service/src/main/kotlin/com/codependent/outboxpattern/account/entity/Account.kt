package com.codependent.outboxpattern.account.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Account(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long,
                   val ownerId: String,
                   var ownerName: String,
                   var funds: Float)
