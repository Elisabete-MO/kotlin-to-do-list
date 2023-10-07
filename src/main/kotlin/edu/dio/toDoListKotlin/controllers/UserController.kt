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
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

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

    @GetMapping
    fun findByUsername(@RequestParam username: String): ResponseEntity<UserCreated> {
        return ResponseEntity.ok(userService.findByUsername(username))
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<UserCreated> {
        return ResponseEntity.ok(userService.findById(id))
    }

    @PostMapping
    fun save(@RequestBody @Valid user: UserDto): ResponseEntity<UserCreated> {
        userService.save(user)
        val newUser = userService.findByUsername(user.username)

    return ResponseEntity
        .created(ServletUriComponentsBuilder.fromCurrentRequest()
//            .queryParam("userId", newUser?.id)
//            .build()
            .path("/{id}")
            .buildAndExpand(newUser?.id)
            .toUri())
        .body(newUser)
    }

    @PutMapping("/{userId}")
    fun update(@PathVariable userId: Long, @RequestBody @Valid userDto: UserDto):
            ResponseEntity<UserCreated> {
        userService.update(userDto.toUser())
        val updatedUser = userService.findById(userId)

        return ResponseEntity
            .ok()
            .body(updatedUser)
    }

    @DeleteMapping("/{userId}")
    fun delete(@PathVariable userId: Long): ResponseEntity<String> {
        userService.delete(userId)
        return ResponseEntity.ok("User $userId deleted " +
                "successfully!")
    }
}
