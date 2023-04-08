package com.group.libraryapp.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.security.SecurityScheme
import org.springdoc.core.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@OpenAPIDefinition(info = Info(title = "library-app", version = "1.0", ))
@Configuration
class SwaggerConfig {
    @Bean
    fun userApiDoc(): GroupedOpenApi =
        GroupedOpenApi
            .builder()
            .group("사용자")
            .pathsToMatch("/user/**")
            .build()

    @Bean
    fun bookApiDoc(): GroupedOpenApi =
        GroupedOpenApi
            .builder()
            .group("도서")
            .pathsToMatch("/book/**")
            .build()

    @Bean
    fun statisticsApiDoc(): GroupedOpenApi =
        GroupedOpenApi
            .builder()
            .group("통계")
            .pathsToMatch("/statistics/**")
            .build()
}
