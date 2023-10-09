package edu.dio.ToDoListKotlin.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import edu.dio.toDoListKotlin.ToDoListKotlinApplication
import edu.dio.toDoListKotlin.controllers.UserController
import edu.dio.toDoListKotlin.controllers.dto.UserCreated
import edu.dio.toDoListKotlin.controllers.dto.UserDto
import edu.dio.toDoListKotlin.models.UserRepository
import edu.dio.toDoListKotlin.models.entities.User
import edu.dio.toDoListKotlin.services.UserService
import org.junit.jupiter.api.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*

@SpringBootTest(classes = [ToDoListKotlinApplication::class])
@ActiveProfiles("test")
@AutoConfigureMockMvc
//@ContextConfiguration(classes = [UserController::class, UserService::class,
//    UserRepository::class])
//@WebMvcTest(UserController::class)
@DisplayName("User Service Tests")
@TestPropertySource(locations = ["classpath:application-test.properties"])
class UserControllerTest {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    companion object {
        const val URL: String = "/users"
    }

//    @BeforeEach fun setUp() {
//        userRepository.deleteAll()
//    }
//
//    @AfterEach fun tearDown() {
//        userRepository.deleteAll()
//    }

//    @Test
//    @DisplayName("1 - User Creation Success- Service Layer")
//    fun testFindAll() {
//        val user: User = createTestUser(1L)
//        userRepository.save(user)
//
//        val response = userRepository.findAll()
//
//        Assertions.assertEquals(1, response.size)
//    }

//    @Test
//    @DisplayName("2 - User Find All Failed - Service Layer")
//    fun testFindAllFail() {
//        val user: User = createTestUser(1L)
//        userRepository.save(user)
//
//        val response = userRepository.findAll()
//
//        Assertions.assertEquals(1, response.size)
//    }

    @Test
    @DisplayName("3 - User Find By Id Success- Integration")
    fun testFindById() {
        val user: User = createTestUser(1L)
        userRepository.save(user)

        mockMvc.perform(MockMvcRequestBuilders.get("$URL/${user.id}")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(user.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(user.username))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(user.email))
            .andExpect(MockMvcResultMatchers.jsonPath("$.imageUrl").value(user.imageUrl))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @DisplayName("4 - User Find By Id Failed - Integration")
    fun testFindByIdFail() {
        val id: Long = 99
        mockMvc.perform(MockMvcRequestBuilders.get("$URL/$id")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Not found"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(404))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value(HttpStatus.NOT_FOUND.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @DisplayName("5 - User Creation Success - Integration")
    fun testSaveUser() {
        val user: UserDto = createTestUserDto()
        val dataUser: String = objectMapper.writeValueAsString(user)

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataUser))
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(user.username))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(user.email))
            .andExpect(MockMvcResultMatchers.jsonPath("$.imageUrl").value(user.imageUrl))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @DisplayName("6 - User Creation Failed - Integration")
    fun testSaveUserFail() {
        userRepository.save(createTestUser(1L))
        val user: UserDto = createTestUserDto()
        val dataUser: String = objectMapper.writeValueAsString(user)

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataUser))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Data Integrity Violation"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value(HttpStatus.BAD_REQUEST.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @DisplayName("7 - User Deleted Success- Integration")
    fun testDeleteUser() {
        val user: User = createTestUser(1L)
        userRepository.save(user)

        mockMvc.perform(MockMvcRequestBuilders.delete("$URL/${user.id}")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$").value("User ${user.id} deleted " +
                    "successfully!"))
            .andDo(MockMvcResultHandlers.print())
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
        Assertions.assertEquals(expected.username, actual.get().username)
        Assertions.assertEquals(expected.email, actual.get().email)
    }
}