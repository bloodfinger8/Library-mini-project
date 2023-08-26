package com.group.libraryapp.domain.user

import com.group.libraryapp.domain.util.Slice

interface UserRepository {
    fun save(user: User): User
    fun saveAll(users: List<User>)
    fun delete(user: User)
    fun deleteAll()
    fun findById(id: Long): User?
    fun findByName(name: String): User?
    fun findByEmail(email: Email): User?
    fun existsByEmail(email: Email): Boolean
    fun findAllByCompanyId(companyId: Long, page: Int, pageSize: Int): Slice<User>
}
