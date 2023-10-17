package com.group.libraryapp.gateway.elasticsearch.regist.book

import co.elastic.clients.elasticsearch.ElasticsearchClient
import co.elastic.clients.elasticsearch.core.DeleteRequest
import co.elastic.clients.elasticsearch.core.IndexRequest
import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.elasticsearch.book.BookSearchEngine
import com.group.libraryapp.domain.elasticsearch.book.doc.BookDoc
import org.slf4j.LoggerFactory

class RegisterBookES(
    private val client: ElasticsearchClient,
    private val index: String
) : BookSearchEngine {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun save(book: Book) {
        client.index(
            IndexRequest.Builder<BookDoc>()
                .index(index)
                .id(book.id.toString())
                .document(BookDoc.create(book))
                .build()
        )
        logger.info("updated es index: $index doc: ${book.id}")
    }

    override fun delete(id: String) {
        client.delete(
            DeleteRequest.Builder()
                .index(index)
                .id(id)
                .build()
        )
        logger.info("removed elasticsearch index: $index doc : $id")
    }
}
