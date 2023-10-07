package edu.dio.toDoListKotlin.controllers

import edu.dio.toDoListKotlin.controllers.dto.TaskDto
import edu.dio.toDoListKotlin.models.entities.Task
import edu.dio.toDoListKotlin.services.TaskService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

/**
 * this [RestController] represents our **Facade**, because it abstracts all
 * complexity of integrations (H2 Database and awesome API) in a simple and
 * cohesive interface (REST API).
 */
@RestController
@RequestMapping(value = ["/tasks"])
class TaskController(private val taskService: TaskService) {
    @GetMapping("/all")
    fun findAll(): ResponseEntity<List<Task>> {
        return ResponseEntity.ok(taskService.findAll())
    }

    @GetMapping
    fun findAllByUserId(@RequestParam userId: Long): ResponseEntity<List<Task>> {
        return ResponseEntity.ok(taskService.findAllByUserId(userId))
    }

    @PostMapping
    fun save(@RequestBody task: TaskDto): ResponseEntity<Task> {
        val newTask: Task = taskService.save(task)
        return ResponseEntity
            .created(ServletUriComponentsBuilder.fromCurrentRequest()
                .queryParam("taskId", newTask.id)
                .build()
                .toUri())
            .body(newTask)
    }

    @PutMapping("{taskId}")
    fun update(@PathVariable taskId: Long, @RequestBody @Valid taskDto: TaskDto):
            ResponseEntity<Task> {
        taskService.update(taskDto.toTask())
        val updatedTask = taskService.findById(taskId)

        return ResponseEntity.ok().body(updatedTask)
    }

    @DeleteMapping("{taskId}")
    fun delete(@PathVariable taskId: Long): ResponseEntity<String> {
        taskService.delete(taskId)
        return ResponseEntity.ok("Task $taskId deleted!")
    }
}
