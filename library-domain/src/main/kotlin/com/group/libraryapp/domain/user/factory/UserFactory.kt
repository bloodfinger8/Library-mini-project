package com.group.libraryapp.domain.user.factory

import com.group.libraryapp.domain.company.Company
import com.group.libraryapp.domain.company.factory.CompanyFactory
import com.group.libraryapp.domain.user.Email
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.loanHistory.UserLoanHistory
import java.time.LocalDateTime

class UserFactory {
    companion object {
        fun create(
            email: Email = Email("example", "example.com"),
            password: String = "12345",
            name: String = "양재우",
            userLoanHistories: MutableList<UserLoanHistory> = mutableListOf(),
            company: Company = CompanyFactory.create(),
            introduction: String? = null,
            createdAt: LocalDateTime = LocalDateTime.now(),
            id: Long? = null,
        ): User {
            val user = User(email, password, name, userLoanHistories, company, introduction, createdAt, id)
            company.join(user)
            return user
        }
    }
}
