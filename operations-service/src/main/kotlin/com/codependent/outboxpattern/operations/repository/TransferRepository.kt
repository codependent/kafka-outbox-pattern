package com.codependent.outboxpattern.operations.repository

import com.codependent.outboxpattern.operations.entity.Transfer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TransferRepository : JpaRepository<Transfer, Int> {

    fun findByTransferId(transferId: String): Transfer?

}
