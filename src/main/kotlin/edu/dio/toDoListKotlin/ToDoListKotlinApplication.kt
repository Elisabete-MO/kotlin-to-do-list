package edu.dio.toDoListKotlin

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@OpenAPIDefinition(
	servers = [Server(
		url = "/",
		description = "default server url"
	)]
)
@EnableJpaRepositories(basePackages = ["edu.dio.toDoListKotlin.models"])
@SpringBootApplication
class ToDoListKotlinApplication

fun main(args: Array<String>) {
	runApplication<ToDoListKotlinApplication>(*args)
}
