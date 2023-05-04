package com.group.libraryapp.usecase.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.book.type.BookType
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanHistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanHistory.type.UserLoanStatus
import com.group.libraryapp.dto.book.command.RegisterBookCommand
import com.group.libraryapp.dto.book.response.BookStatResponse
import com.group.libraryapp.dto.book.response.RegisterBookResponse
import com.group.libraryapp.repository.BookQuerydslRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RegisterBookUseCase(
    val bookRepository: BookRepository,
    val bookQuerydslRepository: BookQuerydslRepository,
    val userRepository: UserRepository,
    val userLoanHistoryRepository: UserLoanHistoryRepository
) {
    @Transactional
    fun register(req: RegisterBookCommand): RegisterBookResponse {
        val book = bookRepository.save(Book.create(req.name, BookType.COMPUTER, req.publisher, req.stock))
        return RegisterBookResponse(book.id!!, book.name, book.type.name, book.publisher, book.stock)
    }

    @Transactional(readOnly = true)
    fun countLoanedBook(): Int {
        return userLoanHistoryRepository.countByStatus(UserLoanStatus.LOANED).toInt()
    }

    @Transactional(readOnly = true)
    fun getStat(): List<BookStatResponse> {
        return bookQuerydslRepository.getStat()
    }
}
