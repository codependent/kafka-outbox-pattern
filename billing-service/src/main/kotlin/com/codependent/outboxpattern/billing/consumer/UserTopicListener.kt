package com.codependent.outboxpattern.billing.consumer

import com.codependent.outboxpattern.billing.entity.UserBillingInfo
import com.codependent.outboxpattern.billing.entity.User
import com.codependent.outboxpattern.billing.service.BillingService
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.messaging.Sink
import org.springframework.stereotype.Component

@Component
class UserTopicListener(private val billingService: BillingService) {

    @StreamListener(Sink.INPUT)
    fun handle(user: User) {
        var userBillingInfo = billingService.get(user.id)
        if(userBillingInfo == null) {
            userBillingInfo = UserBillingInfo(0, user.name, user.address, user.id)
        } else {
            userBillingInfo.name = user.name
            userBillingInfo.address = user.address
        }
        billingService.save(userBillingInfo)
    }

}
