package edu.dio.toDoListKotlin.services

import edu.dio.toDoListKotlin.controllers.dto.UserCreated
import edu.dio.toDoListKotlin.controllers.dto.UserDto
import edu.dio.toDoListKotlin.exceptions.NotFoundException
import edu.dio.toDoListKotlin.models.entities.User
import edu.dio.toDoListKotlin.models.UserRepository
import edu.dio.toDoListKotlin.services.interfaces.UserServiceInterface
import java.util.Optional
import org.springframework.stereotype.Service

/**
 * Implementation of the [UserServiceInterface] interface for the [User] entity.
 */
@Service
class UserService(private val userRepository: UserRepository) : UserServiceInterface
{
    override fun findAll(): List<UserCreated> {
        val users: List<User> = userRepository.findAll()
        if (users.isEmpty()) {
            throw NotFoundException("Nenhum usuário encontrado!")
        }
        return users.map { e -> UserCreated(e.id!!, e.username, e.email, e
            .imageUrl) }
    }

    override fun findById(id: Long): UserCreated? {
        val user: User = userRepository.findById(id)
            .orElseThrow {
                NotFoundException(
                    "Usuário $id não encontrado!"
                )
            }
        return UserCreated(user.id!!, user.username, user.email, user.imageUrl)
    }

    override fun findByUsername(username: String): UserCreated? {
        val user: User = userRepository.findByUsername(username)
            .orElseThrow {
                NotFoundException(
                    "Usuário $username não encontrado!"
                )
            }
        return UserCreated(user.id!!, user.username, user.email, user.imageUrl)
    }

    override fun save(user: UserDto) {
        userRepository.save(user.toUser())
    }

    override fun update(user: User) {
        val existingUser: User = userRepository.findByUsername(user.username)
            .orElseThrow {
                NotFoundException(
                    "Usuário ${user.username} não encontrado!"
                )
            }
        existingUser.apply {
            email = user.email
            imageUrl = user.imageUrl
            userRepository.save(this)
        }
    }

    override fun delete(userId: Long) {
        val user: User = userRepository.findById(userId)
            .orElseThrow {
                NotFoundException("Usuário com ID $userId não encontrado!")
            }
        userRepository.delete(user)
    }
}