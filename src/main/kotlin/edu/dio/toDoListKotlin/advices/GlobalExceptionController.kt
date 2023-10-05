package edu.dio.toDoListKotlin.advices

import edu.dio.toDoListKotlin.exceptions.ExceptionDetails
import edu.dio.toDoListKotlin.exceptions.NotFoundException
import java.time.LocalDateTime
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * Global exception handler.
 */
@RestControllerAdvice
class GlobalExceptionController {
    private val logger = LoggerFactory.getLogger(
        GlobalExceptionController::class.java
    )

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(e: NotFoundException): ResponseEntity<ExceptionDetails> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(
                ExceptionDetails(
                    title = "Not found",
                    timestamp = LocalDateTime.now(),
                    status = HttpStatus.NOT_FOUND.value(),
                    exception = HttpStatus.NOT_FOUND.name,
                    details = mutableMapOf(e.cause.toString() to e.message)
                )
            )
    }
}
