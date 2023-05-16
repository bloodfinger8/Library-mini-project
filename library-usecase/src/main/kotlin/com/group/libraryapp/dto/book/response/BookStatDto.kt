package com.group.libraryapp.dto.book.response

import com.group.libraryapp.domain.book.type.BookType

data class BookStatDto(
    val type: BookType,
    val count: Long
)
