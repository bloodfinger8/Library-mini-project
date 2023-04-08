package com.group.libraryapp.util

import com.group.libraryapp.exception.EmailAlreadyExistsException
import com.group.libraryapp.exception.NotExistLoanBookException
import com.group.libraryapp.exception.NotExistStockException

fun fail(message: String = "request value error"): Nothing = throw IllegalArgumentException(message)

fun loanFail(id: Long?): Nothing = throw NotExistStockException("not exist stock bookId :$id")
fun returnFail(id: Long?): Nothing = throw NotExistLoanBookException("not existed loan bookId :$id")

fun signUpFail(email: String): Nothing = throw EmailAlreadyExistsException("email already exist :$email");