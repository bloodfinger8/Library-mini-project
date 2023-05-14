package com.group.libraryapp.domain.user.loanHistory

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.loanHistory.type.UserLoanStatus
import com.group.libraryapp.exception.fail
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.ZonedDateTime
import javax.persistence.*

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
) {
    @CreationTimestamp
    lateinit var createdAt: ZonedDateTime
    @UpdateTimestamp
    lateinit var updatedAt: ZonedDateTime

    companion object {
        fun create(
            user: User,
            book: Book = Book.create(),
            status: UserLoanStatus = UserLoanStatus.LOANED,
            id: Long? = null
        ): UserLoanHistory {
            return UserLoanHistory(user, book, status, id)
        }
    }

    fun doReturn(book: Book) {
        if (canReturn()) {
            book.changeStock(1)
            this.status = UserLoanStatus.RETURNED
        }
    }

    fun canReturn(): Boolean =
        when (this.status) {
            UserLoanStatus.LOANED -> true
            else -> fail()
        }
}
