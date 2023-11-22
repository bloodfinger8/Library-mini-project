package com.group.libraryapp.domain.user.loanHistory

import com.group.libraryapp.domain.TimeInfoEntity
import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.factory.BookFactory
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.loanHistory.type.UserLoanStatus
import com.group.libraryapp.exception.bookAlreadyLoanFail
import jakarta.persistence.*

@Entity
class UserLoanHistory(
    @ManyToOne
    val user: User,

    @OneToOne
    @JoinColumn(name = "book_id")
    val book: Book,

    @Enumerated(EnumType.STRING)
    var status: UserLoanStatus,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) : TimeInfoEntity() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as UserLoanHistory
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int = id?.hashCode() ?: 0

    companion object {
        fun create(
            user: User,
            book: Book = BookFactory.create(),
            status: UserLoanStatus = UserLoanStatus.LOANED,
            id: Long? = null
        ): UserLoanHistory {
            return UserLoanHistory(user, book, status, id)
        }
    }

    fun doReturn(book: Book) {
        if (canReturn()) {
            book.addStock()
            this.status = UserLoanStatus.RETURNED
        }
    }

    private fun canReturn(): Boolean =
        when (this.status) {
            UserLoanStatus.LOANED -> true
            else -> bookAlreadyLoanFail(this.id)
        }
}
