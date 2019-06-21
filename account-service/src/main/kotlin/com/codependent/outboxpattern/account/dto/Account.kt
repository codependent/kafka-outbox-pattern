package com.codependent.outboxpattern.account.dto

data class Account(val id: Long,
                   val ownerId: String,
                   var ownerName: String,
                   var funds: Float,
                   var movements: MutableList<Movement> = mutableListOf())
