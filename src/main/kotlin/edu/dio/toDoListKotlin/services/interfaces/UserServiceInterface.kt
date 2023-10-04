package edu.dio.toDoListKotlin.services.interfaces

import edu.dio.toDoListKotlin.controllers.dto.UserDto

/**
 * UserService interface for User entity operations that implements the CRUD
 * operations and the strategy pattern for the multiple implementations of the
 * same.
 */
interface UserServiceInterface {
    fun findAll(): List<UserDto>
    fun findByUsername(username: String): UserDto?
    fun save(user: UserDto)
    fun update(user: UserDto)
    fun delete(username: String)
}
