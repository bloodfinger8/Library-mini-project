package com.group.libraryapp.gateway.jpa.user

import com.group.libraryapp.domain.user.Email
import com.group.libraryapp.domain.user.User
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaAdapter : JpaRepository<User, Long> {
    fun findByName(name: String): User?

    fun findByEmail(email: Email): User?

    fun existsByEmail(email: Email): Boolean

    fun findAllByCompanyId(companyId: Long, pageable: Pageable): Slice<User>
}
