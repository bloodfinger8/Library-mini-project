package com.group.libraryapp.usecase.book.dto.response

import com.group.libraryapp.domain.book.Book

data class RegisterBookDto(
    val bookId: Long,
    val name: String,
    val type: String,
    val publisher: String? = null,
    val stock: Int,
) {
    companion object {
        fun of(book: Book): RegisterBookDto {
            return RegisterBookDto(
                bookId = book.id!!,
                name = book.name,
                type = book.type.name,
                publisher = book.publisher,
                stock = book.stock,
            )
        }
    }
}
