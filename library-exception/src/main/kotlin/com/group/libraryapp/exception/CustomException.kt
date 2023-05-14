package com.group.libraryapp.exception

class NotExistStockException(
    override val message: String,
    val code: Int = 40401
) : RuntimeException()

class NotExistLoanBookException(
    override val message: String,
    val code: Int = 40402
) : RuntimeException()

class EmailAlreadyExistsException(
    override val message: String,
    val code: Int = 40403
) : RuntimeException()

class NotExistCompanyException(
    override val message: String,
    val code: Int = 40404
) : RuntimeException()

class InvalidEmailDomainException(
    override val message: String,
    val code: Int = 40405
) : RuntimeException()
