package com.group.libraryapp.usecase.book

import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanHistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanHistory.type.UserLoanStatus
import com.group.libraryapp.dto.book.command.ReturnBookCommand
import com.group.libraryapp.exception.fail
import com.group.libraryapp.exception.returnFail
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReturnBookUseCase (
    val bookRepository: BookRepository,
    val userRepository: UserRepository,
    val userLoanHistoryRepository: UserLoanHistoryRepository
){
    @Transactional
    fun returnBook(command: ReturnBookCommand) {
        val user = userRepository.findByName(command.name) ?: fail()
        val isExistLoanBook = userLoanHistoryRepository.existsByBookIdAndStatus(command.bookId, UserLoanStatus.LOANED)
        when {
            isExistLoanBook -> user.returnBook(bookRepository.findByIdOrNull(command.bookId) ?: fail())
            else -> returnFail(command.bookId)
        }
    }
}