package edu.dio.toDoListKotlin.controllers

import edu.dio.toDoListKotlin.controllers.dto.UserCreated
import edu.dio.toDoListKotlin.controllers.dto.UserDto
import edu.dio.toDoListKotlin.models.entities.User
import edu.dio.toDoListKotlin.services.UserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

/**
 * this [RestController] represents our **Facade**, because it
 * abstracts all complexity of integrations (H2 Database and awesome API) in a
 * simple and cohesive interface (REST API).
 */
@RestController
@RequestMapping(value = ["/users"])
class UserController(private val userService: UserService) {
    @GetMapping("/all")
    fun findAll(): ResponseEntity<List<UserCreated>> {
        return ResponseEntity.ok(userService.findAll())
    }

    @GetMapping("/{username}")
    fun findByUsername(@PathVariable username: String): ResponseEntity<UserCreated> {
        return ResponseEntity.ok(userService.findByUsername(username))
    }

    @PostMapping
    fun save(@RequestBody @Valid user: UserDto): ResponseEntity<UserCreated> {
        userService.save(user)
        val newUser = userService.findByUsername(user.username)

    return ResponseEntity
        .created(ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(newUser?.id)
            .toUri())
        .body(newUser)
    }

    @PutMapping
    fun update(@RequestBody @Valid user: User): ResponseEntity<UserCreated> {
        userService.update(user)
        val updatedUser = userService.findById(user.id)

        return ResponseEntity
            .created(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(updatedUser?.id)
                .toUri())
            .body(updatedUser)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable userId: Long): ResponseEntity<Void> {
        val deleted = userService.delete(userId)
        if (deleted) {
            return ResponseEntity.ok().build()
        } else {
            return ResponseEntity.notFound().build()
        }
    }
}
