package com.group.libraryapp.domain.user

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.user.loanHistory.UserLoanHistory
import javax.persistence.*

@Entity
class User (
    var name: String,
    val age: Int?,
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL] , orphanRemoval = true)
    var userLoanHistories: MutableList<UserLoanHistory> = mutableListOf(),
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
) {

    init {
        if(this.name.isBlank()){
            throw IllegalArgumentException("이름은 필수값 입니다.")
        }
    }
    companion object {
        fun create(
            name: String = "book",
            age: Int? = null,
            userLoanHistories: MutableList<UserLoanHistory> = mutableListOf(),
            id: Long? = null
        ): User {
            return User(name, age, userLoanHistories, id)
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