package edu.dio.toDoListKotlin

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                .title("ToDo List Kotlin API")
                .description("API for managing tasks in a ToDo list")
                .version("1.0.0")
                .contact(Contact().email("emoliv-code@gmail.com"))
                .license(License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0"))
            )
    }
}

//fun publicApi(): GroupedOpenApi? {
//    return GroupedOpenApi.builder()
//        .group("springcreditapplicationsystem-public")
//        .pathsToMatch("/api/customers/**", "/api/credits/**")
//        .build()
//}