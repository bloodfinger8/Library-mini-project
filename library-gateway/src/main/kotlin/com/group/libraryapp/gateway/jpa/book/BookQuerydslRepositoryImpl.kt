package com.group.libraryapp.gateway.jpa.book

import com.group.libraryapp.domain.book.BookQuerydslRepository
import com.group.libraryapp.domain.book.BookStatDto
import com.group.libraryapp.domain.book.QBook.book
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class BookQuerydslRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : BookQuerydslRepository {

    override fun getStat(): List<BookStatDto> {
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
