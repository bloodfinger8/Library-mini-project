package com.group.libraryapp.domain.book

import com.group.libraryapp.domain.util.Slice

interface BookRepository {
    fun save(book: Book): Book
    fun saveAll(books: List<Book>)
    fun findById(id: Long): Book?
    fun findByName(name: String): Book?
    fun findAllByCompanyId(companyId: Long, page: Int, pageSize: Int): Slice<Book>
}
