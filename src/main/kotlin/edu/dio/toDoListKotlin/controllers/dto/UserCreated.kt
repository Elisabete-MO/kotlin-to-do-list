package edu.dio.toDoListKotlin.controllers.dto

import edu.dio.toDoListKotlin.models.entities.User
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

/** Task DTO.
 *
 * @param id id of the person
 * @param username name of the person
 * @param email email of the person
 * @param imageUrl image url of the person
 */
data class UserCreated(
    val id: Long,
    val username: String,
    val email: String,
    val imageUrl: String? = ""
) {
    fun fromUser(): UserCreated = UserCreated(
        id = this.id,
        username = this.username,
        email = this.email,
        imageUrl = this.imageUrl
    )

    fun toUser(): User = User(
        id = this.id,
        username = this.username,
        email = this.email,
        imageUrl = this.imageUrl
    )
}