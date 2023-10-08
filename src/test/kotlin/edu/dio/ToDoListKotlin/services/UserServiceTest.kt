package edu.dio.ToDoListKotlin.services

import edu.dio.toDoListKotlin.controllers.dto.UserCreated
import edu.dio.toDoListKotlin.controllers.dto.UserDto
import edu.dio.toDoListKotlin.exceptions.NotFoundException
import edu.dio.toDoListKotlin.models.UserRepository
import edu.dio.toDoListKotlin.models.entities.User
import edu.dio.toDoListKotlin.services.UserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.function.Executable
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.test.context.ActiveProfiles
import java.util.*
import java.util.Optional.empty

@ExtendWith(MockitoExtension::class)
@DisplayName("User Service Tests")
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

        verify(userRepository, times(1))
            .save(Mockito.any(User::class.java))

        Mockito.verify(userRepository).save(Mockito.argThat({
            it.username == userDto.username &&
            it.email == userDto.email &&
            it.password == userDto.password &&
            it.imageUrl == userDto.imageUrl
        }))
    }

    @Test
    @DisplayName("2 - User Search By ID - Service Layer")
    fun TestFindById() {
        val fakeId: Long = Random().nextLong()
        val user: User = createTestUser(fakeId)

        Mockito.`when`(userRepository.findById(fakeId))
            .thenReturn(Optional.of(user))

        val userData: UserCreated? =  userService.findById(fakeId)

        verify(userRepository, times(1)).findById(fakeId)

        Assertions.assertNotNull(userData)
        Assertions.assertEquals(fakeId, userData?.id)
        Assertions.assertEquals(user.username, userData?.username)
        Assertions.assertEquals(user.email, userData?.email)
        Assertions.assertEquals(user.imageUrl, userData?.imageUrl)
    }

    @Test
    @DisplayName("3 - User Search By ID Failed - Service Layer")
    fun TestFindByIdFail() {
        val fakeId: Long = Random().nextLong()

        Mockito.`when`(userRepository.findById(fakeId))
            .thenReturn(Optional.empty())

        val exception = Assertions.assertThrows(
            NotFoundException::class.java,
            Executable { userService.findById(fakeId) }
        )

        verify(userRepository, times(1)).findById(fakeId)

        Assertions.assertEquals("Usuário $fakeId não encontrado!", exception.message)
        Assertions.assertEquals(NotFoundException::class.java, exception.javaClass)
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