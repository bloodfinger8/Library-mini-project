package com.group.libraryapp.repository

import com.group.libraryapp.domain.book.QBook.book
import com.group.libraryapp.usecase.book.dto.response.BookStatDto
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class BookQuerydslRepository(
    private val queryFactory: JPAQueryFactory
) {
    fun getStat(): List<BookStatDto> {
        return queryFactory
            .select(
                Projections.constructor(
                    BookStatDto::class.java,
                    book.type,
                    book.id.count()
                )
            )
            .from(book)
            .groupBy(book.type)
            .fetch()
    }
}
