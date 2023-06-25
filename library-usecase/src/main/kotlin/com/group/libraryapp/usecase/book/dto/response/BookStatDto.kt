package com.group.libraryapp.usecase.book.dto.response

import com.group.libraryapp.type.book.BookType

data class BookStatDto(
    val type: BookType,
    val count: Long
)
