package com.group.libraryapp.usecase.book

import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanHistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanHistory.type.UserLoanStatus
import com.group.libraryapp.exception.fail
import com.group.libraryapp.exception.returnFail
import com.group.libraryapp.gateway.telegram.Notifier
import com.group.libraryapp.usecase.book.dto.command.ReturnBookCommand
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReturnBookUseCase(
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository,
    private val userLoanHistoryRepository: UserLoanHistoryRepository,
    private val notifier: Notifier
) {
    @Transactional
    fun returnBook(command: ReturnBookCommand) {
        val user = userRepository.findByName(command.name) ?: fail()
        if (!isLoanBook(command)) returnFail(command.bookId)

        val book = bookRepository.findByIdOrNull(command.bookId) ?: fail()
        user.returnBook(book)
        notifier.returned(user.name, book.name)
    }

    private fun isLoanBook(command: ReturnBookCommand) =
        userLoanHistoryRepository.existsByBookIdAndStatus(command.bookId, UserLoanStatus.LOANED)
}
