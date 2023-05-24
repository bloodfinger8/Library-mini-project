package com.group.libraryapp.usecase.book.assembler

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.usecase.book.dto.response.BookDto
import org.springframework.stereotype.Component

@Component
class BookDtoAssembler {
    fun toDtoList(books: List<Book>, user: User): List<BookDto> {
        return books.map { BookDto.of(user, it) }
    }
}
