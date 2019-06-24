package com.codependent.outboxpattern.account.repository

import com.codependent.outboxpattern.account.dto.MovementType
import com.codependent.outboxpattern.account.entity.MovementEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MovementRepository : JpaRepository<MovementEntity, Int> {

    fun findByTransactionIdAndTypeAndAccountEntityId(transactionId: String, type: MovementType, accountEntityId: Long): List<MovementEntity>

}
