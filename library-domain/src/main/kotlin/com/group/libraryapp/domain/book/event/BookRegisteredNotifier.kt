package com.group.libraryapp.domain.book.event

import com.group.libraryapp.domain.book.Book

data class BookRegisteredNotifier(
    val book: Book
)
