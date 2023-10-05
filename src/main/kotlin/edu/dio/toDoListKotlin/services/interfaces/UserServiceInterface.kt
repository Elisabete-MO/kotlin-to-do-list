package edu.dio.toDoListKotlin.services.interfaces

import edu.dio.toDoListKotlin.controllers.dto.UserCreated
import edu.dio.toDoListKotlin.controllers.dto.UserDto
import edu.dio.toDoListKotlin.models.entities.User

/**
 * UserService interface for User entity operations that implements the CRUD
 * operations and the strategy pattern for the multiple implementations of the
 * same.
 */
interface UserServiceInterface {
    fun findAll(): List<UserCreated>
    fun findById(id: Long): UserCreated?
    fun findByUsername(username: String): UserCreated?
    fun save(user: UserDto)
    fun update(user: User)
    fun delete(userId: Long): Boolean
}
