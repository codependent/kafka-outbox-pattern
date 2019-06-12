package com.codependent.outboxpattern.user.repository

import com.codependent.outboxpattern.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Int> {

}
