package com.group.libraryapp.dto.book.response

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.type.BookType
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.loanHistory.type.UserLoanStatus

data class BookInventoryResponse(
    val bookInfos: List<BookInfo>
){
    companion object {
        fun of(user: User, books: List<Book>): BookInventoryResponse {
            val loanedBookIds = user.userLoanHistories
                .filter { userLoanHistory -> userLoanHistory.status == UserLoanStatus.LOANED }
                .map { a -> a.book.id }.toSet()
            return BookInventoryResponse(
                books.map { book -> BookInfo.of(book, loanedBookIds.contains(book.id)) }
            );
        }
    }
}

data class BookInfo(
    var id: Long,
    var name: String,
    var type: BookType,
    var publisher: String?,
    var stock: Int,
    var loaned: Boolean,
){
    companion object {
        fun of(book: Book, contain: Boolean): BookInfo {
            return BookInfo(book.id!!,book.name,book.type,book.publisher,book.stock,contain)
        }
    }
}