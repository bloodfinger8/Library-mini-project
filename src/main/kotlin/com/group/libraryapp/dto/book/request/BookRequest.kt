package com.group.libraryapp.dto.book.request

import com.group.libraryapp.domain.book.type.BookType
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class BookRequest(
    @field:NotBlank
    val name: String,
    var publisher: String? = null,
    @field:NotNull
    var quantity: Int = 1,
    @field:NotNull
    val type: BookType,
)