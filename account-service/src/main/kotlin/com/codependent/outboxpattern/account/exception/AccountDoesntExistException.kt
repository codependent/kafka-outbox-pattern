package com.codependent.outboxpattern.account.exception

class AccountDoesntExistException(sourceAccountId: Long) : RuntimeException(sourceAccountId.toString())
