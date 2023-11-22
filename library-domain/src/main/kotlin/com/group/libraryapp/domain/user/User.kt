package com.group.libraryapp.domain.user

import com.group.libraryapp.domain.TimeInfoEntity
import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.company.Company
import com.group.libraryapp.domain.user.loanHistory.UserLoanHistory
import com.group.libraryapp.exception.bookReturnFail
import jakarta.persistence.*

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
) : TimeInfoEntity() {

    fun update(name: String, introduction: String) {
        this.name = name
        this.introduction = introduction
    }

    fun loanBook(book: Book) {
        if (book.canLoanBook()) {
            book.removeStock()
            writeHistory(book)
        }
    }

    fun returnBook(book: Book) {
        val userLoanHistory = this.userLoanHistories.firstOrNull { it.book == book } ?: bookReturnFail(book.id)
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

    private fun writeHistory(book: Book) {
        this.userLoanHistories.add(UserLoanHistory.create(this, book))
    }
}
