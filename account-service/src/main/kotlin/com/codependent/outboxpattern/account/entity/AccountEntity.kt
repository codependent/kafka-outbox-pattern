package com.codependent.outboxpattern.account.entity

import com.codependent.outboxpattern.account.dto.Movement
import javax.persistence.*

@Entity
data class AccountEntity(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long,
                         val ownerId: String,
                         var ownerName: String,
                         var funds: Float,
                         @OneToMany(cascade = [CascadeType.ALL]) var movements: MutableList<MovementEntity> = mutableListOf())
