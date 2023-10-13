package edu.dio.toDoListKotlin.services

import edu.dio.toDoListKotlin.controllers.dto.TaskDto
import edu.dio.toDoListKotlin.exceptions.NotFoundException
import edu.dio.toDoListKotlin.models.entities.Task
import edu.dio.toDoListKotlin.models.TaskRepository
import edu.dio.toDoListKotlin.models.entities.User
import edu.dio.toDoListKotlin.services.interfaces.TaskServiceInterface
import jakarta.transaction.Transactional
import java.util.Optional
import org.springframework.stereotype.Service

/**
 * Implementation of the [TaskServiceInterface] interface for the [Task] entity.
 */
@Service
class TaskService(private val taskRepository: TaskRepository) : TaskServiceInterface
{
    override fun findAll(): List<Task> {
        val tasks: List<Task> = taskRepository.findAll()
        if (tasks.isEmpty()) {
            throw NotFoundException("Nenhuma tarefa encontrada!")
        }
        return tasks.map { e -> Task(e.id, e.date, e.title, e.description, e
            .status, e.user) }
    }

    override fun findAllByUserId(userId: Long): List<Task> {
        val tasks: List<Task> = taskRepository.findAllByUserId(userId)
        if (tasks.isEmpty()) {
                throw NotFoundException(
                    "Nenhuma tarefa encontrada para o usuário $userId!"
                )
            }
        return tasks.map { e -> Task(e.id, e.date, e.title, e.description, e
            .status, e.user) }
    }

    override fun findById(id: Long): Task? {
        val task: Task = taskRepository.findById(id)
            .orElseThrow {
                NotFoundException(
                    "Tarefa $id não encontrada!"
                )
            }
        return Task(task.id, task.date, task.title, task.description, task
            .status, task.user)
    }

    override fun save(task: TaskDto): Task {
        return taskRepository.save(task.toTask())
    }

    @Transactional
    override fun update(task: Task) {
        val existingTask: Task = taskRepository.findById(task.id!!)
            .orElseThrow {
                NotFoundException(
                    "Tarefa ${task.id} não encontrada!"                )
            }
        existingTask.apply {
            date = task.date
            status = task.status
            title = task.title
            description = task.description
        }
        taskRepository.save(existingTask)
    }

    override fun delete(taskId: Long) {
        val task: Task? = this.findById(taskId)
        taskRepository.delete(task!!)
    }
}