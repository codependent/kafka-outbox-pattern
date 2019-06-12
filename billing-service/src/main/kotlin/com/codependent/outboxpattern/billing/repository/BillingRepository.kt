package com.codependent.outboxpattern.billing.repository

import com.codependent.outboxpattern.billing.entity.UserBillingInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BillingRepository : JpaRepository<UserBillingInfo, Int> {

}
