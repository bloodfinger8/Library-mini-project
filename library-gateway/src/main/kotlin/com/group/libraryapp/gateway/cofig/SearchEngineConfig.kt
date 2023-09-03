package com.group.libraryapp.gateway.cofig

import co.elastic.clients.elasticsearch.ElasticsearchClient
import co.elastic.clients.json.jackson.JacksonJsonpMapper
import co.elastic.clients.transport.rest_client.RestClientTransport
import com.group.libraryapp.domain.elasticsearch.book.BookSearchEngine
import com.group.libraryapp.domain.elasticsearch.book.BookSearcher
import com.group.libraryapp.gateway.elasticsearch.find.book.SearchBookES
import com.group.libraryapp.gateway.elasticsearch.regist.book.RegisterBookES
import org.apache.http.HttpHost
import org.elasticsearch.client.RestClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SearchEngineConfig(
    @Value("\${elasticsearch.index.book}") val bookIndex: String
) {
    @Bean
    fun elasticSearchClient(@Value("\${elasticsearch.endPoint}") endPoint: String): ElasticsearchClient? {
        return ElasticsearchClient(
            RestClientTransport(
                RestClient.builder(
                    *(endPoint.split(",").map { HttpHost.create(it) }.toTypedArray())
                ).build(),
                JacksonJsonpMapper()
            )
        )
    }

    @Bean
    fun userSearchEngine(elasticsearchClient: ElasticsearchClient): BookSearchEngine =
        RegisterBookES(elasticsearchClient, bookIndex)

    @Bean
    fun userSearcher(elasticsearchClient: ElasticsearchClient): BookSearcher =
        SearchBookES(elasticsearchClient, bookIndex)
}
