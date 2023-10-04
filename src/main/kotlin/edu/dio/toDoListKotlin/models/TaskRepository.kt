package edu.dio.toDoListKotlin.models

import edu.dio.toDoListKotlin.models.entities.Task
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * TaskRepository.
 */
@Repository
interface TaskRepository : JpaRepository<Task, Long> {
    fun findAllByUserId(userId: Long): List<Task>
}
