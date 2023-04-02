package com.group.libraryapp.exception


class NotExistStockException(
    override val message: String,
    val code: Int = 40401
): RuntimeException()

class NotExistLoanBookException(
    override val message: String,
    val code: Int = 40402
): RuntimeException()