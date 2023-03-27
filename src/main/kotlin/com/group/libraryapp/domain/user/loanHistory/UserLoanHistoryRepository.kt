package com.group.libraryapp.domain.user.loanHistory

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.user.loanHistory.type.UserLoanStatus
import org.springframework.data.jpa.repository.JpaRepository

interface UserLoanHistoryRepository : JpaRepository<UserLoanHistory, Long>{
    fun findByBookNameAndStatus(bookName: String, status: UserLoanStatus): UserLoanHistory?

    fun countByStatus(status: UserLoanStatus): Long

    fun existsByBookIdAndStatus(bookId: Long, status: UserLoanStatus): Boolean
}