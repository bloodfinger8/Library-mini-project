package com.group.libraryapp.domain.user.loanHistory

import com.group.libraryapp.domain.user.loanHistory.type.UserLoanStatus
import org.springframework.data.jpa.repository.JpaRepository

interface UserLoanHistoryRepositroy : JpaRepository<UserLoanHistory, Long>{
    fun findByBookNameAndStatus(bookName: String, status: UserLoanStatus): UserLoanHistory?
}