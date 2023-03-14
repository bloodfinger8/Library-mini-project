package com.group.libraryapp.domain.user

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.user.loanHistory.UserLoanHistory
import org.springframework.security.crypto.password.PasswordEncoder
import javax.persistence.*

@Entity
class User (

    var email: Email,
    var password: String,
    var name: String,
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL] , orphanRemoval = true)
    var userLoanHistories: MutableList<UserLoanHistory> = mutableListOf(),
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
) {

    companion object {
        fun create(
            email: Email = Email("example@example.com"),
            password: String = "12345",
            name: String = "양재우",
            passwordEncoder: PasswordEncoder,
            userLoanHistories: MutableList<UserLoanHistory> = mutableListOf(),
            id: Long? = null,
        ): User {
            return User(email, passwordEncoder.encode(password), name, userLoanHistories ,id)
        }
    }

    fun updateName(name: String) {
        this.name = name
    }

    fun loanBook(book: Book) {
        this.userLoanHistories.add(UserLoanHistory.create(this, book.name))
    }

    fun returnBook(bookName: String) {
        this.userLoanHistories.first { userLoanHistory -> userLoanHistory.bookName == bookName }.doReturn()
    }
}