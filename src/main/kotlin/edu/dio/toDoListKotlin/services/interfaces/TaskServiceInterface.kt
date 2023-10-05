package edu.dio.toDoListKotlin.services.interfaces

import edu.dio.toDoListKotlin.controllers.dto.TaskDto
import edu.dio.toDoListKotlin.models.entities.Task

/**
 * TaskService interface for Task entity operations that implements the CRUD
 * operations and the strategy pattern for the multiple implementations of the
 * same.
 */
interface TaskServiceInterface {
    fun findAll(): List<Task>
    fun findAllByUserId(userId: Long): List<Task>
    fun save(task: TaskDto)
    fun update(task: Task)
    fun delete(task: Task)
}
