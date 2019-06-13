package com.codependent.outboxpattern.account.web

import com.codependent.outboxpattern.account.entity.Account
import com.codependent.outboxpattern.account.service.AccountService
import com.codependent.outboxpattern.account.dto.Transfer
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/accounts")
class AccountRestController(private val accountService: AccountService) {

    @PostMapping
    fun save(@RequestBody account: Account) {
        accountService.save(account)
    }

    @PutMapping("/transfers")
    fun update(@RequestBody transfer: Transfer) {
        accountService.transfer(transfer)
    }

    @GetMapping
    fun getAll(): List<Account> {
        return accountService.getAll()
    }
}
