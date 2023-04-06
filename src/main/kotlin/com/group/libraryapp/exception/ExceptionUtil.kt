package com.group.libraryapp.util

import com.group.libraryapp.exception.EmailAlreadyExistsException
import com.group.libraryapp.exception.NotExistLoanBookException
import com.group.libraryapp.exception.NotExistStockException
import org.springframework.security.authentication.BadCredentialsException

fun fail(message: String = "request value error"): Nothing = throw IllegalArgumentException(message)

fun loanFail(id: Long?): Nothing = throw NotExistStockException("not exist stock bookId :$id")
fun returnFail(id: Long?): Nothing = throw NotExistLoanBookException("not existed loan bookId :$id")

fun signUpFail(email: String): Nothing = throw EmailAlreadyExistsException("email already exist :$email");

fun loginFail(): Nothing = throw BadCredentialsException(badCredentialMessage)
const val badCredentialMessage = "id/pw not matched"