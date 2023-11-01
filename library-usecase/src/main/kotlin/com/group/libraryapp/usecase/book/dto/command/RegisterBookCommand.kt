package com.group.libraryapp.usecase.book.dto.command

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.factory.BookFactory
import com.group.libraryapp.domain.company.Company
import com.group.libraryapp.type.book.BookType

class RegisterBookCommand(
    val name: String,
    val publisher: String? = null,
    val stock: Int = 1,
    val type: String,
    val location: String? = null,
    val companyId: Long
) {
    fun createBook(company: Company): Book {
        return BookFactory.create(this.name, BookType.COMPUTER, this.publisher, this.stock, this.location, company)
    }
}
