package com.group.libraryapp.gateway.elasticsearch

import co.elastic.clients.elasticsearch.ElasticsearchClient
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery
import co.elastic.clients.elasticsearch.core.SearchRequest
import co.elastic.clients.elasticsearch.core.SearchResponse
import org.slf4j.LoggerFactory
import kotlin.system.measureTimeMillis

class QueryExecutor<T>(
    private val client: ElasticsearchClient,
    private val indexNames: Array<String>,
    private val typeClass: Class<T>
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    fun search(
        queryBuilder: BoolQuery.Builder,
        page: Int,
        pageSize: Int
    ): QueryResult<T> {
        val esPaging = ESPaging(page, pageSize)
        val searchRequest = SearchRequest.Builder().query(queryBuilder.build()._toQuery())
        val query = applyPagingQuery(searchRequest, esPaging)

        val searchResponse = executeSearch(query)
        return processResult(searchResponse, esPaging)
    }

    private fun applyPagingQuery(
        queryBuilder: SearchRequest.Builder,
        esPaging: ESPaging
    ): SearchRequest.Builder {
        return queryBuilder.from(esPaging.getFrom()).size(esPaging.getFetchSize())
    }

    private fun executeSearch(queryBuilder: SearchRequest.Builder): SearchResponse<T> {
        val result: SearchResponse<T>
        val queryBuild = queryBuilder.index(indexNames.toList()).build()
        val timeInMillis = measureTimeMillis {
            result = client.search(queryBuild, typeClass)
        }
        if (timeInMillis > 1000) {
            logger.info("ES_SLOW elasticsearch query indexes [${indexNames.joinToString(",")}] elapsed: ${timeInMillis / 1000f} query: $queryBuild ")
        }
        return result
    }

    private fun processResult(res: SearchResponse<T>, esPaging: ESPaging): QueryResult<T> {
        if (res.hits().hits().isEmpty()) return QueryResult()

        val elements = res.hits().hits().toList()
        val total = res.hits().total()?.value() ?: 0L

        if (elements.size <= esPaging.getPageSize()) {
            return QueryResult(false, total, elements)
        }

        return QueryResult(true, total, elements.subList(0, esPaging.getPageSize()))
    }
}
