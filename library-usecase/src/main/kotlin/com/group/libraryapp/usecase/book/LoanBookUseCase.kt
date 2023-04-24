package com.group.libraryapp.usecase.book

import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.book.command.LoanBookCommand
import com.group.libraryapp.exception.fail
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LoanBookUseCase (
    val bookRepository: BookRepository,
    val userRepository: UserRepository,
){
    @Transactional
    fun loan(loanBookCommand: LoanBookCommand) {
        val book = bookRepository.findByIdOrNull(loanBookCommand.bookId) ?: fail()
        val user = userRepository.findByName(loanBookCommand.name) ?: fail()
        user.loanBook(book)
    }
}