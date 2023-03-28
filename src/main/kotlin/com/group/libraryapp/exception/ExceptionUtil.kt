package com.group.libraryapp.util

import com.group.libraryapp.exception.NotExistStock
import org.springframework.http.HttpStatus

fun fail(message: String = "request value error"): Nothing = throw IllegalArgumentException(message)

fun loanFail(id: Long?): Nothing = throw NotExistStock("not exist stock book :$id")