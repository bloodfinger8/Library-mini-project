package com.group.libraryapp.usecase.book.dto.response

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.type.BookType
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.loanHistory.type.UserLoanStatus

data class BookDto(
    var id: Long,
    var name: String,
    var type: BookType,
    var publisher: String?,
    var stock: Int,
    val location: String?,
    var loaned: Boolean,
) {
    companion object {
        fun of(user: User, book: Book): BookDto {
            val loanedBookIds = user.userLoanHistories
                .filter { it.status == UserLoanStatus.LOANED }
                .map { it.book.id }.toSet()
            return BookDto(
                id = book.id!!,
                name = book.name,
                type = book.type,
                publisher = book.publisher,
                stock = book.stock,
                location = book.location,
                loaned = loanedBookIds.contains(book.id),
            )
        }
    }
}
