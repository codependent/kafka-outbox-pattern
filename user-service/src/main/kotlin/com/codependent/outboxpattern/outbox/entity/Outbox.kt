package com.codependent.outboxpattern.outbox.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Outbox(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Int,
                  val topic: String,
                  var state: State,
                  val payload: String)
