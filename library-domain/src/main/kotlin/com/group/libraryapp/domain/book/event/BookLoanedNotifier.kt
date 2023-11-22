package com.group.libraryapp.domain.book.event

data class BookLoanedNotifier(
    val userName: String,
    val bookName: String
)
