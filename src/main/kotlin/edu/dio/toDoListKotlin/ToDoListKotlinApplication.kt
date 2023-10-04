package edu.dio.toDoListKotlin

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@OpenAPIDefinition(
	servers = [Server(
		url = "/",
		description = "default server url"
	)]
)
@SpringBootApplication
class ToDoListKotlinApplication

fun main(args: Array<String>) {
	runApplication<ToDoListKotlinApplication>(*args)
}
