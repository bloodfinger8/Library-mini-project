package com.group.libraryapp.gateway.elasticsearch.find.book

import co.elastic.clients.elasticsearch.ElasticsearchClient
import com.group.libraryapp.domain.elasticsearch.SearchResult
import com.group.libraryapp.domain.elasticsearch.book.BookSearcher
import com.group.libraryapp.domain.elasticsearch.book.GetBookSpec
import com.group.libraryapp.gateway.elasticsearch.QueryExecutor
import com.group.libraryapp.gateway.elasticsearch.QueryResult
import com.group.libraryapp.gateway.elasticsearch.regist.book.BookDoc
import java.util.function.Function

class SearchBookES(
    private val client: ElasticsearchClient,
    private val index: String
) : BookSearcher {

    override fun search(spec: GetBookSpec): SearchResult<String> {
        return execute(arrayOf(index)) {
            it.search(spec.build(), spec.page, spec.pageSize)
        }
    }

    private fun execute(
        indexes: Array<String>,
        fn: Function<QueryExecutor<BookDoc>, QueryResult<BookDoc>>
    ): SearchResult<String> {
        val executor = QueryExecutor(client, indexes, BookDoc::class.java)
        val queryResult = fn.apply(executor)
        return SearchResult(
            queryResult.hasNext,
            queryResult.total,
            queryResult.map {
                Doc2BookBuilder.create(it)
            }
        )
    }
}
