package com.codependent.outboxpattern.user.service

import com.codependent.outboxpattern.user.entity.User

interface UserService {

    fun save(user: User)
    fun getAll(): List<User>
    fun update(user: User)

}
