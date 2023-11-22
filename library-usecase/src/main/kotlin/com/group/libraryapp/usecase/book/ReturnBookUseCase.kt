package com.group.libraryapp.usecase.book

import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.book.event.BookReturnedNotifier
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanHistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanHistory.type.UserLoanStatus
import com.group.libraryapp.exception.bookNotFoundFail
import com.group.libraryapp.exception.bookReturnFail
import com.group.libraryapp.exception.userNotFoundFail
import com.group.libraryapp.usecase.book.dto.command.ReturnBookCommand
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReturnBookUseCase(
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository,
    private val userLoanHistoryRepository: UserLoanHistoryRepository,
    private val eventPublisher: ApplicationEventPublisher
) {
    @Transactional
    fun returnBook(command: ReturnBookCommand) {
        val user = userRepository.findByName(command.name) ?: userNotFoundFail(command.name)
        if (!isLoanedBook(command)) bookReturnFail(command.bookId)

        val book = bookRepository.findById(command.bookId) ?: bookNotFoundFail(command.bookId)
        user.returnBook(book)
        eventPublisher.publishEvent(BookReturnedNotifier(user.name, book.name))
    }

    private fun isLoanedBook(command: ReturnBookCommand) =
        userLoanHistoryRepository.existsByBookIdAndStatus(command.bookId, UserLoanStatus.LOANED)
}
