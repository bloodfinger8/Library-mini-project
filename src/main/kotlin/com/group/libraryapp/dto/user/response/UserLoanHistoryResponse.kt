package com.group.libraryapp.dto.user.response

data class UserLoanHistoryResponse(
    val name: String,
    val books: List<BookLoanHistoryResponse>
)

data class BookLoanHistoryResponse(
    val name: String,
    val isReturn: Boolean
)
