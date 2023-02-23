package com.group.libraryapp.domain.user.loanHistory

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserLoanHistoryRepositroy : JpaRepository<UserLoanHistory, Long>{
    fun findByBookNameAndIsReturn(bookName: String, isReturn: Boolean): UserLoanHistory?
}