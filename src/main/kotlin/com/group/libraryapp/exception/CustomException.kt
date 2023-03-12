package com.group.libraryapp.exception

import org.springframework.http.HttpStatus

class CustomException(
    val httpStatus: HttpStatus,
    override val message: String
): RuntimeException()