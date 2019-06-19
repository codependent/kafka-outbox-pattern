package com.codependent.outboxpattern.transfer.web

import com.codependent.outboxpattern.transfer.entity.Transfer
import com.codependent.outboxpattern.transfer.service.TransferService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/transfers")
class TransferRestController(private val transferService: TransferService) {

    @GetMapping
    fun getAll(): List<Transfer> {
        return transferService.getAll()
    }
}
