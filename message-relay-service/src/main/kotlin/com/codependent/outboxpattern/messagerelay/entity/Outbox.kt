package com.codependent.outboxpattern.messagerelay.entity

import javax.persistence.*

@Entity
data class Outbox(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Int,
                  val messageKey: String,
                  val topic: String,
                  val contentType: String,
                  var state: State,
                  @Lob val payload: ByteArray)
