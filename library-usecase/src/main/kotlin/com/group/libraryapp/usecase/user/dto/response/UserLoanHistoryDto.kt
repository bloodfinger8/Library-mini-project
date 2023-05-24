package com.group.libraryapp.usecase.user.dto.response

import com.group.libraryapp.domain.user.Email
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.loanHistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanHistory.type.UserLoanStatus

data class UserLoanHistoryDto(
    val email: Email,
    val books: List<BookLoanHistoryDto>
) {
    companion object {
        fun from(user: User): UserLoanHistoryDto {
            return UserLoanHistoryDto(
                user.email,
                user.userLoanHistories.map { BookLoanHistoryDto.from(it) }
            )
        }
    }
}

data class BookLoanHistoryDto(
    val name: String,
    val isReturn: Boolean
) {
    companion object {
        fun from(userLoanHistory: UserLoanHistory): BookLoanHistoryDto {
            return BookLoanHistoryDto(
                userLoanHistory.book.name,
                userLoanHistory.status == UserLoanStatus.RETURNED
            )
        }
    }
}
