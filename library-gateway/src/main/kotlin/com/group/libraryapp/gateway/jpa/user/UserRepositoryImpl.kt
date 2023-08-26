package com.group.libraryapp.gateway.jpa.user

import com.group.libraryapp.domain.user.Email
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.util.Slice
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val userJpaAdapter: UserJpaAdapter
) : UserRepository {

    override fun save(user: User): User {
        return userJpaAdapter.save(user)
    }

    override fun saveAll(users: List<User>) {
        userJpaAdapter.saveAll(users)
    }

    override fun delete(user: User) {
        userJpaAdapter.delete(user)
    }

    override fun deleteAll() {
        userJpaAdapter.deleteAll()
    }

    override fun findById(id: Long): User? {
        return userJpaAdapter.findByIdOrNull(id)
    }

    override fun findByName(name: String): User? {
        return userJpaAdapter.findByName(name)
    }

    override fun findByEmail(email: Email): User? {
        return userJpaAdapter.findByEmail(email)
    }

    override fun existsByEmail(email: Email): Boolean {
        return userJpaAdapter.existsByEmail(email)
    }

    override fun findAllByCompanyId(companyId: Long, page: Int, pageSize: Int): Slice<User> {
        val result = userJpaAdapter.findAllByCompanyId(companyId, PageRequest.of(page, pageSize))
        return Slice(result.hasNext(), result.content)
    }
}
