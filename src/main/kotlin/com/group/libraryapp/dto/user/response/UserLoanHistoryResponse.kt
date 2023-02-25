package com.group.libraryapp.dto.user.response

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.loanHistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanHistory.type.UserLoanStatus

data class UserLoanHistoryResponse(
    val name: String,
    val books: List<BookLoanHistoryResponse>
){
    companion object {
        fun of(user: User): UserLoanHistoryResponse {
            return UserLoanHistoryResponse(user.name,
                user.userLoanHistories.map (BookLoanHistoryResponse::of)
            )
        }
    }

}

data class BookLoanHistoryResponse(
    val name: String,
    val isReturn: Boolean
){
    companion object {
        fun of(userLoanHistory: UserLoanHistory): BookLoanHistoryResponse {
            return BookLoanHistoryResponse(userLoanHistory.bookName,
                userLoanHistory.status == UserLoanStatus.RETURNED)
        }
    }
}


