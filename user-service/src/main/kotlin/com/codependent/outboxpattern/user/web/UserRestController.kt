package com.codependent.outboxpattern.user.web

import com.codependent.outboxpattern.user.entity.User
import com.codependent.outboxpattern.user.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserRestController(private val userService: UserService) {

    @PostMapping
    fun save(@RequestBody user: User) {
        userService.save(user)
    }

    @PutMapping
    fun update(@RequestBody user: User) {
        userService.update(user)
    }

    @GetMapping
    fun getAll(): List<User> {
        return userService.getAll()
    }
}
