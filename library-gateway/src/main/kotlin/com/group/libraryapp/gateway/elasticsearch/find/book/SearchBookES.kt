package com.group.libraryapp.gateway.elasticsearch.find.book

import co.elastic.clients.elasticsearch.ElasticsearchClient
import com.group.libraryapp.domain.elasticsearch.SearchResult
import com.group.libraryapp.domain.elasticsearch.book.BookSearcher
import com.group.libraryapp.domain.elasticsearch.book.GetBookSpec
import com.group.libraryapp.domain.elasticsearch.book.doc.BookDoc
import com.group.libraryapp.gateway.elasticsearch.QueryExecutor
import com.group.libraryapp.gateway.elasticsearch.QueryResult

class SearchBookES(
    private val client: ElasticsearchClient,
    private val index: String
) : BookSearcher {

    override fun search(spec: GetBookSpec): SearchResult<BookDoc> {
        return execute(arrayOf(index)) {
            it.search(spec.build(), spec.page, spec.pageSize)
        }
    }

    private fun execute(
        indexes: Array<String>,
        fn: (QueryExecutor<BookDoc>) -> QueryResult<BookDoc>
    ): SearchResult<BookDoc> {
        val executor = QueryExecutor(client, indexes, BookDoc::class.java)
        val queryResult = fn(executor)
        return SearchResult(
            queryResult.hasNext,
            queryResult.total,
            queryResult.map { Doc2BookBuilder.create(it) }
        )
    }
}
