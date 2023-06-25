package com.group.libraryapp.domain.book.factory

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.company.Company
import com.group.libraryapp.type.book.BookType

class BookFactory {
    companion object {
        fun create(
            name: String = "book name",
            type: BookType = BookType.COMPUTER,
            publisher: String? = null,
            stock: Int = 1,
            location: String? = null,
            company: Company? = null,
            version: Long = 1,
            id: Long? = null
        ): Book {
            return Book(name, type, publisher, stock, location, company, version, id)
        }
    }
}
