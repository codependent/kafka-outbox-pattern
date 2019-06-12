package com.codependent.outboxpattern.billing.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class UserBillingInfo(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Int,
                           var name: String,
                           var address: String,
                           var userId: Int)
