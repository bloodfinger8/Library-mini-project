package com.group.libraryapp.domain.book

import com.group.libraryapp.type.book.BookType

data class BookStatDto(
    val type: BookType,
    val count: Long
)
