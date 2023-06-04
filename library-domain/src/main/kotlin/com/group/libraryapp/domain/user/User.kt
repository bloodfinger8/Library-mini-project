package com.group.libraryapp.domain.user

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.company.Company
import com.group.libraryapp.domain.user.loanHistory.UserLoanHistory
import com.group.libraryapp.exception.fail
import java.time.LocalDateTime
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.OneToOne

@Entity(name = "users")
class User(
    val email: Email,
    val password: String,
    var name: String,

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var userLoanHistories: MutableList<UserLoanHistory> = mutableListOf(),

    @OneToOne
    @JoinColumn(name = "company_id")
    val company: Company? = null,

    var introduction: String? = null,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
) {
    var updatedAt: LocalDateTime = createdAt

    fun update(name: String, introduction: String) {
        this.name = name
        this.introduction = introduction
        this.updatedAt = LocalDateTime.now()
    }

    fun loanBook(book: Book) {
        if (book.canLoanBook()) {
            book.changeStock(-1)
            this.userLoanHistories.add(UserLoanHistory.create(this, book))
        }
    }

    fun returnBook(book: Book) {
        val userLoanHistory = this.userLoanHistories.firstOrNull { it.book == book } ?: fail()
        userLoanHistory.doReturn(book)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as User
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int = id?.hashCode() ?: 0
}
