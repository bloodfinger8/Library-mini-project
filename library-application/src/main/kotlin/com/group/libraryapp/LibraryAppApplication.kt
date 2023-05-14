package com.group.libraryapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@SpringBootApplication
class LibraryAppApplication {
    @GetMapping(path = ["ping"])
    fun ping() = "pong"
}

fun main(args: Array<String>) {
    runApplication<LibraryAppApplication>(*args)
}
