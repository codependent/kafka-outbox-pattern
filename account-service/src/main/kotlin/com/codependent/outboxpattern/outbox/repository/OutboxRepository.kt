package com.codependent.outboxpattern.outbox.repository

import com.codependent.outboxpattern.outbox.entity.Outbox
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OutboxRepository : JpaRepository<Outbox, Int>
