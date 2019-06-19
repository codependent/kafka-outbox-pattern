package com.codependent.outboxpattern.operations.web

import com.codependent.outboxpattern.operations.entity.Transfer
import com.codependent.outboxpattern.operations.service.TransferService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/transfers")
class TransferRestController(private val transferService: TransferService) {

    @GetMapping
    fun getAll(): List<Transfer> {
        return transferService.getAll()
    }
}
