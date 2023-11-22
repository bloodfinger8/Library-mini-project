package com.group.libraryapp.exception

import org.springframework.security.authentication.BadCredentialsException

// user exception
fun userNotFoundFail(id: Long): Nothing = throw IllegalArgumentException("not existed userId :$id")
fun userNotFoundFail(value: String): Nothing = throw IllegalArgumentException("not existed value :$value")

// book exception
fun bookNotFoundFail(id: Long): Nothing = throw IllegalArgumentException("not existed bookId :$id")
fun bookReturnFail(id: Long?): Nothing = throw NotExistLoanBookException("not existed loan bookId :$id")
fun bookLoanFail(id: Long?): Nothing = throw NotExistStockException("not exist stock bookId :$id")
fun bookAlreadyLoanFail(id: Long?): Nothing = throw AlreadyLoanBookException("already loan bookId :$id")

// email exception
fun signUpFail(email: String): Nothing = throw EmailAlreadyExistsException("email already exist :$email")
fun loginFail(): Nothing = throw BadCredentialsException(badCredentialMessage)
fun invalidEmailFail(domain: String): Nothing =
    throw InvalidEmailDomainException("invalid domain of user email :$domain")

// company exception
fun companyNotFoundFail(companyId: Long): Nothing = throw NotExistCompanyException("not existed companyId :$companyId")

const val badCredentialMessage = "id/pw not matched"
