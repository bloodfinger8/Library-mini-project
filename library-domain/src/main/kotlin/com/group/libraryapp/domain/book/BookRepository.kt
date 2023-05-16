package com.group.libraryapp.domain.book

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository : JpaRepository<Book, Long> {
    fun findByName(name: String): Book?

    fun findAllByCompanyId(companyId: Long, pageable: Pageable): Slice<Book>
}
