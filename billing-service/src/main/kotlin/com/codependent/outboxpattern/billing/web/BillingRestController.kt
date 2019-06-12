package com.codependent.outboxpattern.billing.web

import com.codependent.outboxpattern.billing.entity.UserBillingInfo
import com.codependent.outboxpattern.billing.service.BillingService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/userBillingInfos")
class BillingRestController(private val billingService: BillingService) {

    @GetMapping
    fun getAll(): List<UserBillingInfo> {
        return billingService.getAll()
    }
}
