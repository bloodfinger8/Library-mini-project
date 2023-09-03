package com.group.libraryapp.gateway.elasticsearch.find.book

import co.elastic.clients.elasticsearch.core.search.Hit
import com.group.libraryapp.gateway.elasticsearch.regist.book.BookDoc

object Doc2BookBuilder {
    fun create(hit: Hit<BookDoc>): String {
        if (hit.source() == null) {
            throw IllegalStateException("hit.source() is null")
        }
        return hit.source()!!.id
    }
}
