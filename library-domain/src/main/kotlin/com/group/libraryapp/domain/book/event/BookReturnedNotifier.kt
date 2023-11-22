package com.group.libraryapp.domain.book.event

data class BookReturnedNotifier(
    val userName: String,
    val bookName: String
)
