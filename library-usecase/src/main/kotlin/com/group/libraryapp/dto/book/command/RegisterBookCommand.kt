package com.group.libraryapp.dto.book.command

import com.group.libraryapp.domain.book.type.BookType

class RegisterBookCommand(
    val name: String,
    val publisher: String? = null,
    val stock: Int = 1,
    val type: BookType,
)