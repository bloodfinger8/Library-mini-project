package com.group.libraryapp.usecase.book.dto.response

data class RegisterBookDto(
    val bookId: Long,
    val name: String,
    val type: String,
    val publisher: String? = null,
    val stock: Int,
)
