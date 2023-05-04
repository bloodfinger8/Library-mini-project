package com.group.libraryapp.dto.book.response

data class RegisterBookResponse(
    val bookId: Long,
    val name: String,
    val type: String,
    val publisher: String? = null,
    val stock: Int,
)
