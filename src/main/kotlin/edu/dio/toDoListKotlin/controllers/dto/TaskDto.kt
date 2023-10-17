package edu.dio.toDoListKotlin.controllers.dto

import edu.dio.toDoListKotlin.models.entities.Task
import edu.dio.toDoListKotlin.models.entities.User
import jakarta.persistence.Column
import jakarta.validation.constraints.FutureOrPresent
import java.time.LocalDate
import java.time.LocalDate.now

/** Task DTO. Transforms a TaskDto into a Task.
 *
 * @param date date of the task to be done
 * @param title title of the task
 * @param description description of the task
 * @param status status of the task
 * @param userId id of the user who owns the task
 */
data class TaskDto(
    @field:FutureOrPresent (message = "Date must be in the future or present")
    val date: LocalDate = now(),

    var title: String?,

    @Column(nullable = false)
    var description: String,

    var status: Task.StatusEnum = Task.StatusEnum.TODO,

    var userId: Long
) {
    fun toTask(): Task = Task(
        id = null,
        date = this.date,
        title = this.title,
        description = this.description,
        status = this.status,
        user = User(id = userId)
    )
}
