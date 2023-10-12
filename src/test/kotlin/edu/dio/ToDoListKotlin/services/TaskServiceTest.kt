package edu.dio.ToDoListKotlin.services

import edu.dio.toDoListKotlin.controllers.dto.TaskDto
import edu.dio.toDoListKotlin.controllers.dto.UserDto
import edu.dio.toDoListKotlin.exceptions.NotFoundException
import edu.dio.toDoListKotlin.models.TaskRepository
import edu.dio.toDoListKotlin.models.entities.Task
import edu.dio.toDoListKotlin.models.entities.User
import edu.dio.toDoListKotlin.services.TaskService
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
import java.time.LocalDate
import java.util.*
import java.util.Optional.empty

@ExtendWith(MockitoExtension::class)
@DisplayName("Task Service Layer Tests")
@ActiveProfiles("test")
class TaskServiceTest {

    @Mock private lateinit var taskRepository: TaskRepository

    @InjectMocks private lateinit var taskService: TaskService

    @Test
    @DisplayName("1 - Task Creation Success")
    fun TestTaskCreation() {
        val fakeId: Long = Random().nextLong()
        val taskDto: TaskDto = createTestTaskDto()

        Mockito.`when`(taskRepository.save(Mockito.any(Task::class.java)))
            .thenReturn(Task(fakeId))

        taskService.save(taskDto)

        verify(taskRepository, times(1))
            .save(Mockito.any(Task::class.java))

        Mockito.verify(taskRepository).save(Mockito.argThat({
            it.date == taskDto.date &&
            it.title == taskDto.title &&
            it.description == taskDto.description &&
            it.status == taskDto.status
        }))
    }


    @Test
    @DisplayName("2 - Task Search By ID")
    fun TestFindById() {
        val fakeId: Long = Random().nextLong()
        val task: Task = createTestTask(fakeId)

        Mockito.`when`(taskRepository.findById(fakeId))
            .thenReturn(Optional.of(task))

        val taskData: Task? =  taskService.findById(fakeId)

        verify(taskRepository, times(1)).findById(fakeId)

        Assertions.assertNotNull(taskData)
        Assertions.assertEquals(fakeId, taskData?.id)
        Assertions.assertEquals(task.date, taskData?.date)
        Assertions.assertEquals(task.title, taskData?.title)
        Assertions.assertEquals(task.description, taskData?.description)
        Assertions.assertEquals(task.status, taskData?.status)
    }

    @Test
    @DisplayName("3 - Task Search By ID Failed")
    fun TestFindByIdFail() {
        val fakeId: Long = Random().nextLong()

        Mockito.`when`(taskRepository.findById(fakeId))
            .thenReturn(Optional.empty())

        val exception = Assertions.assertThrows(
            NotFoundException::class.java,
            Executable { taskService.findById(fakeId) }
        )

        verify(taskRepository, times(1)).findById(fakeId)

        Assertions.assertEquals("Tarefa $fakeId não encontrada!", exception
            .message)
        Assertions.assertEquals(NotFoundException::class.java, exception.javaClass)
    }

    // Helper methods
    private fun createTestUser(): User {
        return User(1L, "Test", "teste@email.com", "123456", "")
    }
    private fun createTestTaskDto(): TaskDto {
        return TaskDto(LocalDate.parse("2023-05-05"), "Test","Test Task", Task.StatusEnum.TODO, 1L)
    }

    private fun createTestTask(id: Long): Task {
        val task: Task = createTestTaskDto().toTask()
        return Task(id, LocalDate.parse("2023-05-05"), task.title, task
            .description, task.status, task.user)
    }

//    private fun assertTaskEquals(
//        expected: TaskDto,
//        actual: Optional<TaskDto>
//    ) {
//        Assertions.assertNotNull(actual)
//        assertEquals(expected.title, actual.get().title)
//        assertEquals(expected.description, actual.get().description)
//    }
}