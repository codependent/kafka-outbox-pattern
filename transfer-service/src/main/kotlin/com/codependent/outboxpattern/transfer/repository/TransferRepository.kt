package com.codependent.outboxpattern.transfer.repository

import com.codependent.outboxpattern.transfer.entity.Transfer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TransferRepository : JpaRepository<Transfer, Int>
