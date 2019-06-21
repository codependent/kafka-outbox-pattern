package com.codependent.outboxpattern.account.repository

import com.codependent.outboxpattern.account.dto.Account
import com.codependent.outboxpattern.account.entity.AccountEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : JpaRepository<AccountEntity, Long> {

}
