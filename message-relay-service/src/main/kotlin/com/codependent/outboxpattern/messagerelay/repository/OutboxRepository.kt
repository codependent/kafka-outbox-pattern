package com.codependent.outboxpattern.messagerelay.repository

import com.codependent.outboxpattern.messagerelay.entity.Outbox
import com.codependent.outboxpattern.messagerelay.entity.State
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.stereotype.Repository
import javax.persistence.LockModeType

@Repository
interface OutboxRepository : JpaRepository<Outbox, Int> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findByState(state: State): List<Outbox>

}
