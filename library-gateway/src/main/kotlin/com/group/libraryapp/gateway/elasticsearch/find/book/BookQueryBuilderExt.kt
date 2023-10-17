package com.group.libraryapp.gateway.elasticsearch.find.book

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery
import com.group.libraryapp.domain.elasticsearch.book.GetBookSpec

fun GetBookSpec.build(): BoolQuery.Builder {
    return BookQueryBuilder()
        .addQuery(title)
        .addCompanyIdFilter(companyId.toString())
        .build()
}
