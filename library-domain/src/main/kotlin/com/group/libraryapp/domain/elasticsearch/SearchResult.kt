package com.group.libraryapp.domain.elasticsearch

data class SearchResult<T>(
    val hasNext: Boolean = false,
    val total: Long = 0,
    val elements: List<T> = emptyList()
) : List<T> by elements
