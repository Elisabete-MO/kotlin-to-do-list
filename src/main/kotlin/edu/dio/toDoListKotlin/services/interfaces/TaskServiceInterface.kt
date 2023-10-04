package edu.dio.toDoListKotlin.services.interfaces

import edu.dio.toDoListKotlin.controllers.dto.TaskDto

/**
 * TaskService interface for Task entity operations that implements the CRUD
 * operations and the strategy pattern for the multiple implementations of the
 * same.
 */
interface TaskServiceInterface {
    fun findAll(): List<TaskDto>
    fun findAllByUserId(userId: Long): List<TaskDto>
    fun save(task: TaskDto)
    fun update(task: TaskDto)
    fun delete(task: TaskDto)
}
