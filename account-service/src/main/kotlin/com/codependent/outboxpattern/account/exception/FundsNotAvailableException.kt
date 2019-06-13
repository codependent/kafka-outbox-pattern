package com.codependent.outboxpattern.account.exception

class FundsNotAvailableException(val sourceAccountId: Long) : RuntimeException()
