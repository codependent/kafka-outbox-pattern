package com.codependent.outboxpattern.billing.service

import com.codependent.outboxpattern.billing.entity.UserBillingInfo
import com.codependent.outboxpattern.billing.repository.BillingRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class BillingServiceImpl(private val billingRepository: BillingRepository) : BillingService {

    override fun getAll(): List<UserBillingInfo> {
        return billingRepository.findAll()
    }

    override fun get(id: Int): UserBillingInfo? {
        return billingRepository.findById(id).orElse(null)
    }

    override fun save(userBillingInfo: UserBillingInfo) {
        billingRepository.save(userBillingInfo)
    }

}
