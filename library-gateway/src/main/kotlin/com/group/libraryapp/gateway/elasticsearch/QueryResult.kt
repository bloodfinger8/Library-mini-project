package com.group.libraryapp.gateway.elasticsearch

import co.elastic.clients.elasticsearch.core.search.Hit

data class QueryResult<T>(
    val hasNext: Boolean = false,
    var total: Long = 0,
    val elements: List<Hit<T>> = emptyList()
) : List<Hit<T>> by elements
