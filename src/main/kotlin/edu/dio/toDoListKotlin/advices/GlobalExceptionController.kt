package edu.dio.toDoListKotlin.advices

import edu.dio.toDoListKotlin.exceptions.ExceptionDetails
import edu.dio.toDoListKotlin.exceptions.NotFoundException
import java.time.LocalDateTime
import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
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

    fun headers() = HttpHeaders().apply {
        contentType = MediaType.APPLICATION_JSON
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(e: NotFoundException): ResponseEntity<ExceptionDetails> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .headers(headers())
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

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrityViolation(e: DataIntegrityViolationException):
            ResponseEntity<ExceptionDetails> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .headers(headers())
            .body(
                ExceptionDetails(
                    title = "Data Integrity Violation",
                    timestamp = LocalDateTime.now(),
                    status = HttpStatus.BAD_REQUEST.value(),
                    exception = HttpStatus.BAD_REQUEST.name,
                    details = mutableMapOf(e.cause.toString() to e.message)
                )
            )
    }

    @ExceptionHandler(MethodArgumentNotValidException ::class)
    fun handleDataIntegrityViolation(e: MethodArgumentNotValidException):
            ResponseEntity<ExceptionDetails> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .headers(headers())
            .body(
                ExceptionDetails(
                    title = "Argument Not Valid",
                    timestamp = LocalDateTime.now(),
                    status = HttpStatus.BAD_REQUEST.value(),
                    exception = HttpStatus.BAD_REQUEST.name,
                    details = mutableMapOf(e.cause.toString() to e.message)
                )
            )
    }

    @ExceptionHandler(Throwable::class)
    fun handleThrowable(e: Throwable): ResponseEntity<ExceptionDetails> {
        logger.error(e.message, e)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .headers(headers())
            .body(
                ExceptionDetails(
                    title = "Internal Server Error",
                    timestamp = LocalDateTime.now(),
                    status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    exception = HttpStatus.INTERNAL_SERVER_ERROR.name,
                    details = mutableMapOf(e.cause.toString() to e.message)
                )
            )
    }

}
