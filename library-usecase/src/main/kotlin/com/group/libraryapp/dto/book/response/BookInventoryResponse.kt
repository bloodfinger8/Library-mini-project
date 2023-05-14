package com.group.libraryapp.dto.book.response

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.type.BookType
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.loanHistory.type.UserLoanStatus
import org.springframework.data.domain.Page

data class BookInventoryResponse(
    val books: List<BookInfo>,
    val hasNext: Boolean
) {
    companion object {
        fun of(user: User, books: Page<Book>): BookInventoryResponse {
            val loanedBookIds = user.userLoanHistories
                .filter { userLoanHistory -> userLoanHistory.status == UserLoanStatus.LOANED }
                .map { a -> a.book.id }.toSet()
            return BookInventoryResponse(
                books.content.map { book -> BookInfo.of(book, loanedBookIds.contains(book.id)) },
                books.hasNext()
            )
        }
    }
}

data class BookInfo(
    var id: Long,
    var name: String,
    var type: BookType,
    var publisher: String?,
    var stock: Int,
    val location: String?,
    var loaned: Boolean,
) {
    companion object {
        fun of(book: Book, contain: Boolean): BookInfo {
            return BookInfo(book.id!!, book.name, book.type, book.publisher, book.stock, book.location, contain)
        }
    }
}
