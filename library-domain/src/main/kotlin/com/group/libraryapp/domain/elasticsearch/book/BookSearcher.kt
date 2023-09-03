package com.group.libraryapp.domain.elasticsearch.book

import com.group.libraryapp.domain.elasticsearch.SearchResult

interface BookSearcher {
    fun search(spec: GetBookSpec): SearchResult<String>
}
