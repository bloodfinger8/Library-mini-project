package com.group.libraryapp.gateway.elasticsearch.regist.book

import com.group.libraryapp.domain.book.Book

data class BookDoc(
    val id: String = "",
    val name: String = "",
    val stock: String = "",
    val type: String = "",
    val publisher: String? = "",
    val location: String? = "",
    val companyId: String? = "",
    val createdAt: String = ""
) {
    companion object {
        fun create(book: Book): BookDoc {
            return BookDoc(
                id = book.id.toString(),
                name = book.name,
                stock = book.stock.toString(),
                type = book.getMyType(),
                publisher = book.publisher,
                location = book.location,
                companyId = book.company?.id.toString(),
                createdAt = book.createdAt.toString()
            )
        }
    }
}
