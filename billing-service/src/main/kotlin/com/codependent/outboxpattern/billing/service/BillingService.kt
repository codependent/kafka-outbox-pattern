package com.codependent.outboxpattern.billing.service

import com.codependent.outboxpattern.billing.entity.UserBillingInfo

interface BillingService {

    fun save(userBillingInfo: UserBillingInfo)
    fun getAll(): List<UserBillingInfo>
    fun get(id: Int): UserBillingInfo?

}
