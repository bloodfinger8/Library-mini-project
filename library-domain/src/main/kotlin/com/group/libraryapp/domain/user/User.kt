package com.group.libraryapp.domain.user

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.company.Company
import com.group.libraryapp.domain.user.loanHistory.UserLoanHistory
import com.group.libraryapp.exception.fail
import java.time.LocalDateTime
import javax.persistence.*

@Entity(name = "users")
class User (
    val email: Email,
    val password: String,
    var name: String,

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.ALL] , orphanRemoval = true)
    var userLoanHistories: MutableList<UserLoanHistory> = mutableListOf(),

    @OneToOne
    @JoinColumn(name = "company_id")
    val company: Company? = null,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
) {
    var updatedAt: LocalDateTime = createdAt

    companion object {
        fun create(
            email: Email = Email("example", "example.com"),
            password: String = "12345",
            name: String = "양재우",
            userLoanHistories: MutableList<UserLoanHistory> = mutableListOf(),
            company: Company = Company.create(),
            createdAt: LocalDateTime = LocalDateTime.now(),
            id: Long? = null,
        ): User {
            val user = User(email, password, name, userLoanHistories, company, createdAt, id)
            company.join(user)
            return user
        }
    }

    fun updateName(name: String) {
        this.name = name
    }

    fun loanBook(book: Book) {
        if(book.canLoanBook()) {
            book.changeStock(-1)
            this.userLoanHistories.add(UserLoanHistory.create(this, book))
        }
    }

    fun returnBook(book: Book) {
        val userLoanHistory = this.userLoanHistories.firstOrNull { userLoanHistory -> userLoanHistory.book == book } ?: fail()
        userLoanHistory.doReturn(book)
    }
}