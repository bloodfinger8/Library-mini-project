package com.group.libraryapp.domain.elasticsearch.book

import com.group.libraryapp.domain.elasticsearch.SearchResult
import com.group.libraryapp.domain.elasticsearch.book.doc.BookDoc

interface BookSearcher {
    fun search(spec: GetBookSpec): SearchResult<BookDoc>
}
