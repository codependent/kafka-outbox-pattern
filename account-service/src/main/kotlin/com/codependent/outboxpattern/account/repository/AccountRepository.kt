package com.codependent.outboxpattern.account.repository

import com.codependent.outboxpattern.account.entity.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : JpaRepository<Account, Long> {

}
