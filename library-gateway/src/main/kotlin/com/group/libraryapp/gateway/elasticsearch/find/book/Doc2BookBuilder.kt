package com.group.libraryapp.gateway.elasticsearch.find.book

import co.elastic.clients.elasticsearch.core.search.Hit
import com.group.libraryapp.domain.elasticsearch.book.doc.BookDoc

object Doc2BookBuilder {
    fun create(hit: Hit<BookDoc>): BookDoc =
        if (hit.source() != null) hit.source()!! else throw IllegalStateException("hit.source() is null")
}
