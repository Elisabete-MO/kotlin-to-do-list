package edu.dio.toDoListKotlin.exceptions;

/**
 * This exception is thrown when the given data is not found.
 */
data class NotFoundException(override val message: String?): RuntimeException(message) {}
