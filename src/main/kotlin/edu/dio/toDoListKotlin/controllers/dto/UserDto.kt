package edu.dio.toDoListKotlin.controllers.dto

import edu.dio.toDoListKotlin.models.entities.User
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

/** Task DTO.
 *
 * @param username name of the person
 * @param email email of the person
 * @param imageUrl image url of the person
 * @param password password of the person
 */
data class UserDto(
    @field:NotEmpty(message = "Invalid input")
    val username: String,

    @field:Email(message = "Invalid email")
    @field:NotEmpty(message = "Invalid input")
    val email: String,

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#\$%!\\-_?&])" +
            "([A-Za-z\\d@#\$%!\\-_?&]+)\$", message = "Password must contain at least one uppercase letter, " +
            "one lowercase letter, one number and one special character")
    @field:Size(min = 6, message = "Password must be at least 6 characters long")
    @field:NotEmpty(message = "Password is required")
    val password: String,

    val imageUrl: String
) {

    fun toUser(): User = User(
        id = null,
        username = this.username,
        email = this.email,
        imageUrl = this.imageUrl,
        password = this.password,
//        address = Address(
//            zipCode = this.zipCode,
//            street = this.street
//        ),
    )
}
