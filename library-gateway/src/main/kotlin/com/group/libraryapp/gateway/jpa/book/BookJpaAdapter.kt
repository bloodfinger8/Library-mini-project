package com.group.libraryapp.gateway.jpa.book

import com.group.libraryapp.domain.book.Book
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository

interface BookJpaAdapter : JpaRepository<Book, Long> {
    fun findByName(name: String): Book?

    fun findAllByCompanyId(companyId: Long, pageable: Pageable): Slice<Book>
}
