package com.group.libraryapp.domain.elasticsearch.book

import com.group.libraryapp.domain.book.Book

interface BookSearchEngine {
    fun save(book: Book)
    fun delete(id: String)
}
