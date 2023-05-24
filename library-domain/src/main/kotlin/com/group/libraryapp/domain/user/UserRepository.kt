package com.group.libraryapp.domain.user

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByName(name: String): User?

    fun findByEmail(email: Email): User?

    fun existsByEmail(email: Email): Boolean

    fun findAllByCompanyId(companyId: Long, pageable: Pageable): Slice<User>
}
