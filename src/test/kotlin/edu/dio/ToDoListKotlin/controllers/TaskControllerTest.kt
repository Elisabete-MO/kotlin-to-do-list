package edu.dio.ToDoListKotlin.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import edu.dio.toDoListKotlin.ToDoListKotlinApplication
import edu.dio.toDoListKotlin.controllers.TaskController
import edu.dio.toDoListKotlin.controllers.dto.TaskDto
import edu.dio.toDoListKotlin.models.TaskRepository
import edu.dio.toDoListKotlin.models.UserRepository
import edu.dio.toDoListKotlin.models.entities.Task
import edu.dio.toDoListKotlin.models.entities.User
import edu.dio.toDoListKotlin.services.TaskService
import edu.dio.toDoListKotlin.services.UserService
import jakarta.transaction.Transactional
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
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
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.client.match.MockRestRequestMatchers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import java.time.LocalDate
import java.util.*

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [ToDoListKotlinApplication::class])
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DisplayName("Task Integration Tests")
@TestPropertySource(locations = ["classpath:application-test.properties"])
class TaskControllerTest {

    @Autowired
    private lateinit var taskRepository: TaskRepository

    @Autowired
    private lateinit var taskService: TaskService

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    companion object {
        const val URL: String = "/tasks"
    }

    @BeforeEach
    fun setUp() {
        taskRepository.deleteAll()
        userRepository.deleteAll()
    }

    @AfterEach
    fun tearDown() {
        taskRepository.deleteAll()
        userRepository.deleteAll()
    }

    @Test
    @DisplayName("1 - Task Creation Success")
    @Transactional
    fun testSaveTask() {
        val user: User = createTestUser()
        userRepository.save(user)

        val task: TaskDto = createTestTaskDto(user)
        val dataTask: String = objectMapper.writeValueAsString(task)

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataTask))
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.description").value
                (task.description))
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(task.title))
            .andExpect(MockMvcResultMatchers.jsonPath("$.user.id").value(task
                .userId))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @DisplayName("2 - Task Creation Failed - Duplicate Entry")
    fun testSaveTaskFail() {
        val user: User = createTestUser()
        val task: TaskDto = createTestTaskDto(user)
        task.userId = 99L
        val dataTask: String = objectMapper.writeValueAsString(task)

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataTask))
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
    @DisplayName("3 - Task Update Success")
    @Transactional
    fun testUpdateTask() {
        val user: User = createTestUser()
        userRepository.save(user)

        val task: Task = createTestTask(1L, user)
        taskRepository.save(task)

        val updateTask: TaskDto = createTestTaskDto(user)
        updateTask.status = Task.StatusEnum.COMPLETED
        val dataTask: String = objectMapper.writeValueAsString(updateTask)

        mockMvc.perform(MockMvcRequestBuilders.put("$URL/${task.id}")
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataTask))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(task.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$.date").value(updateTask
                .date.toString()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(updateTask.title))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(updateTask
                .status.toString()))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @DisplayName("4 - Task Update Failed")
    fun testUpdateTaskFail() {
        val user: User = createTestUser()
        val fakeId: Long = Random().nextLong()
        val updateTask: TaskDto = createTestTaskDto(user)
        val dataTask: String = objectMapper.writeValueAsString(updateTask)

        mockMvc.perform(MockMvcRequestBuilders.put("$URL/$fakeId")
            .contentType(MediaType.APPLICATION_JSON)
            .content(dataTask))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Not found"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(404))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value(HttpStatus.NOT_FOUND.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andExpect(MockMvcResultMatchers.jsonPath("$.details.null").value
                ("Tarefa $fakeId não encontrada!"))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @DisplayName("5 - Find All Tasks Success")
    fun testFindAll() {
        val user: User = createTestUser()
        userRepository.save(user)

        val fakeId: Long = Random().nextLong()
        val fakeId1: Long = Random().nextLong()
        val task: Task = createTestTask(fakeId, user)
        val task1: Task = createTestTask(fakeId1, user)

        taskRepository.save(task)
        taskRepository.save(task1)

        mockMvc.perform(MockMvcRequestBuilders.get("$URL/all")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType
                .APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(task.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].date").value(task
                .date.toString()))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value(task.title))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].status").value
                (task.status.toString()))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @DisplayName("6 - Find All Tasks Failed")
    fun testFindAllFail() {
        mockMvc.perform(MockMvcRequestBuilders.get("$URL/all")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Not found"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(404))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value(HttpStatus.NOT_FOUND.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andExpect(MockMvcResultMatchers.jsonPath("$.details.null").value
                ("Nenhuma tarefa encontrada!"))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @DisplayName("7 - Find All Tasks By User Id Success")
    fun testFindByUserId() {
        val user: User = createTestUser()
        userRepository.save(user)

        val task: Task = createTestTask(1, user)
        val task1: Task = createTestTask(2, user)

        taskRepository.save(task)
        taskRepository.save(task1)

        mockMvc.perform(MockMvcRequestBuilders.get("$URL?userId=${user.id}")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(task.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].date").value(task
                .date.toString()))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value(task.title))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].status").value
                (task.status.toString()))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @DisplayName("8 - Find All Tasks By User Id Failed")
    fun testFindByUserIdFail() {
        val id: Long = 99
        mockMvc.perform(MockMvcRequestBuilders.get("$URL?userId=$id")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Not found"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(404))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value(HttpStatus.NOT_FOUND.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andExpect(MockMvcResultMatchers.jsonPath("$.details.null").value
                ("Nenhuma tarefa encontrada para o usuário $id!"))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @DisplayName("9 - Task Deleted Success")
    fun testDeleteTask() {
        val user: User = createTestUser()
        userRepository.save(user)

        val fakeId: Long = Random().nextLong()
        val task: Task = createTestTask(fakeId, user)
        taskRepository.save(task)

        mockMvc.perform(MockMvcRequestBuilders.delete("$URL/${task.id}")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$").value("Task ${task.id} deleted " +
                    "successfully!"))
            .andDo(MockMvcResultHandlers.print())
    }

    // Helper methods

    private fun createTestUser(): User {
        val userId: Long = 1L
        return User(userId, "Test", "teste@email.com", "123456", "")
    }

    private fun createTestTaskDto(user: User): TaskDto {
        return TaskDto(LocalDate.now(), "Test","Test Task", Task.StatusEnum
            .TODO, user.id!!)
    }

    private fun createTestTask(id: Long, user: User): Task {
        val task: Task = createTestTaskDto(user).toTask()
        return Task(id, task.date, task.title, task.description, task.status, user)
    }
}
