package com.group.libraryapp.exception

import org.springframework.http.HttpStatus

class CustomException(
    val httpStatus: HttpStatus,
    override val message: String
): RuntimeException()

class NotExistStock(
    override val message: String,
    val httpStatus: HttpStatus = HttpStatus.NOT_FOUND
): RuntimeException()