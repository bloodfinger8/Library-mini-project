package com.group.libraryapp.dto.book.response

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.type.BookType
import com.group.libraryapp.usecase.UserLoanHistoryCollection

class BookListResponse(
    var id: Long,
    var name: String,
    var type: BookType,
    var publisher: String?,
    var stock: Int,
){
    var isLoaned: Boolean = false

    constructor(book: Book, userLoanHistoryCollection: UserLoanHistoryCollection) : this(book.id!!,book.name,book.type,book.publisher,book.stock) {
//        userLoanHistoryCollection
//        this.isLoaned =
    }

}