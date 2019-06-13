package com.codependent.outboxpattern.messagerelay.web

import com.codependent.outboxpattern.messagerelay.entity.Outbox
import com.codependent.outboxpattern.messagerelay.service.OutboxService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/outbox")
class OutboxRestController(private val outboxService: OutboxService) {

    @GetMapping
    fun getAll(): List<Outbox> {
        return outboxService.getAll()
    }

}
