package com.codependent.outboxpattern.user.service

import com.codependent.outboxpattern.outbox.service.OutboxService
import com.codependent.outboxpattern.user.entity.User
import com.codependent.outboxpattern.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class UserServiceImpl(private val userRepository: UserRepository,
                      private val outboxService: OutboxService) : UserService {

    override fun getAll(): List<User> {
        return userRepository.findAll()
    }

    override fun save(user: User) {
        userRepository.save(user)
        outboxService.save("user", user)

    }

    override fun update(user: User) {
        userRepository.save(user)
        outboxService.save("user", user)
    }

}
