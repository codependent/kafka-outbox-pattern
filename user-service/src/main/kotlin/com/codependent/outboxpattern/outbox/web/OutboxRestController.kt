package com.codependent.outboxpattern.outbox.web

import com.codependent.outboxpattern.outbox.entity.Outbox
import com.codependent.outboxpattern.outbox.service.OutboxService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/outbox")
class OutboxRestController(private val outboxService: OutboxService) {

    @GetMapping
    fun getAll(): List<Outbox> {
        return outboxService.getAll()
    }

}
