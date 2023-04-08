package com.group.libraryapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@EntityScan(basePackages = ["com.group.libraryapp.domain"])
@EnableJpaRepositories(basePackages = ["com.group.libraryapp.domain"])
@SpringBootApplication(scanBasePackages = ["com.group.libraryapp"])
class LibraryAppApplication {
    @GetMapping(path = ["ping"])
    fun ping() = "pong"
}

fun main(args: Array<String>) {
    runApplication<LibraryAppApplication>(*args)
}