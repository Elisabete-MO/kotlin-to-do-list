package edu.dio.ToDoListKotlin.services

import edu.dio.toDoListKotlin.controllers.dto.UserCreated
import edu.dio.toDoListKotlin.controllers.dto.UserDto
import edu.dio.toDoListKotlin.models.UserRepository
import edu.dio.toDoListKotlin.models.entities.User
import edu.dio.toDoListKotlin.services.UserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import java.util.*

@ExtendWith(MockitoExtension::class)
@DisplayName("User Service Test")
@ActiveProfiles("test")
class UserServiceTest {

    @Mock private lateinit var userRepository: UserRepository

    @InjectMocks private lateinit var userService: UserService

    @Test
    @DisplayName("1 - User Creation Success- Service Layer")
    fun TestUserCreation() {
        val userDto: UserDto = createTestUserDto()

        Mockito.`when`(userRepository.save(Mockito.any(User::class.java)))
            .thenReturn(User(1L))

        userService.save(userDto)

        Mockito.verify(userRepository).save(Mockito.argThat({
            it.username == userDto.username &&
            it.email == userDto.email &&
            it.password == userDto.password &&
            it.imageUrl == userDto.imageUrl
        }))
    }

    // Helper methods
    private fun createTestUserDto(): UserDto {
        return UserDto("Test", "teste@email.com", "123456", "")
    }

    private fun createTestUser(id: Long): User {
        val user: User = createTestUserDto().toUser()
        return User(id, user.username, user.email, user.password, user.imageUrl)
    }

    private fun createTestUserCreated(id: Long): UserCreated {
        val user: User = createTestUserDto().toUser()
        return UserCreated(id, user.username, user.email, user.imageUrl)
    }

    private fun assertUserEquals(
        expected: UserDto,
        actual: Optional<UserDto>
    ) {
        Assertions.assertNotNull(actual)
        assertEquals(expected.username, actual.get().username)
        assertEquals(expected.email, actual.get().email)
    }
}