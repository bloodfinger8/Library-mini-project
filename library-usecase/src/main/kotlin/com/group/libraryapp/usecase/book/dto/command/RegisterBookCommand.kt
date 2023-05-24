package com.group.libraryapp.usecase.book.dto.command

class RegisterBookCommand(
    val name: String,
    val publisher: String? = null,
    val stock: Int = 1,
    val type: String,
    val location: String? = null,
    val companyId: Long
)
