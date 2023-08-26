package com.group.libraryapp.gateway.jpa.user.loanHistory

import com.group.libraryapp.domain.user.loanHistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanHistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanHistory.type.UserLoanStatus
import org.springframework.stereotype.Repository

@Repository
class UserLoanHistoryRepositoryImpl(
    private val adapter: UserLoanHistoryJpaAdapter
) : UserLoanHistoryRepository {
    override fun saveAll(userLoanHistories: List<UserLoanHistory>) {
        adapter.saveAll(userLoanHistories)
    }

    override fun findByBookNameAndStatus(bookName: String, status: UserLoanStatus): UserLoanHistory? {
        return adapter.findByBookNameAndStatus(bookName, status)
    }

    override fun countByStatus(status: UserLoanStatus): Long {
        return adapter.countByStatus(status)
    }

    override fun existsByBookIdAndStatus(bookId: Long, status: UserLoanStatus): Boolean {
        return adapter.existsByBookIdAndStatus(bookId, status)
    }
}
