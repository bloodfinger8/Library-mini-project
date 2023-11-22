package com.group.libraryapp.usecase.book

import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.book.event.BookLoanedNotifier
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.exception.bookNotFoundFail
import com.group.libraryapp.exception.userNotFoundFail
import com.group.libraryapp.usecase.book.dto.command.LoanBookCommand
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LoanBookUseCase(
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository,
    private val eventPublisher: ApplicationEventPublisher
) {
    @Transactional
    fun loan(cmd: LoanBookCommand) {
        val book = bookRepository.findById(cmd.bookId) ?: bookNotFoundFail(cmd.bookId)
        val user = userRepository.findByName(cmd.name) ?: userNotFoundFail(cmd.name)
        user.loanBook(book)
        eventPublisher.publishEvent(BookLoanedNotifier(user.name, book.name))
    }
}
