package edu.dio.toDoListKotlin.services.interfaces

import edu.dio.toDoListKotlin.controllers.dto.UserCreated
import edu.dio.toDoListKotlin.controllers.dto.UserDto

/**
 * UserService interface for User entity operations that implements the CRUD
 * operations and the strategy pattern for the multiple implementations of the
 * same.
 */
interface UserServiceInterface {
    fun findAll(): List<UserCreated>
    fun findByUsername(username: String): UserCreated?
    fun save(user: UserDto)
    fun update(user: UserDto)
    fun delete(username: String)
}
