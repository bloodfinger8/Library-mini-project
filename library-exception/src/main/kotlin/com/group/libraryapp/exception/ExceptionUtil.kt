package com.group.libraryapp.exception

import org.springframework.security.authentication.BadCredentialsException

fun fail(message: String = "request value error"): Nothing = throw IllegalArgumentException(message)

fun loanFail(id: Long?): Nothing = throw NotExistStockException("not exist stock bookId :$id")
fun returnFail(id: Long?): Nothing = throw NotExistLoanBookException("not existed loan bookId :$id")

fun signUpFail(email: String): Nothing = throw EmailAlreadyExistsException("email already exist :$email")
fun companyNotFoundFail(companyId: Long): Nothing = throw NotExistCompanyException("not existed companyId :$companyId")
fun invalidEmailFail(domain: String): Nothing = throw InvalidEmailDomainException("invalid domain of user email :$domain")

fun loginFail(): Nothing = throw BadCredentialsException(badCredentialMessage)
const val badCredentialMessage = "id/pw not matched"
