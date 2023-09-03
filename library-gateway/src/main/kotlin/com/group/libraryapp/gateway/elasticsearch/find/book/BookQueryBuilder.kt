package com.group.libraryapp.gateway.elasticsearch.find.book

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery
import co.elastic.clients.elasticsearch._types.query_dsl.PrefixQuery
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders

class BookQueryBuilder {
    private val queryBuilder: BoolQuery.Builder = QueryBuilders.bool()

    fun addQuery(query: String) = apply {
        queryBuilder.filter(
            QueryBuilders.prefix {
                PrefixQuery.Builder().field(BookFields.BOOK_NAME).value(query)
            }
        )
    }

    fun build() = queryBuilder
}
