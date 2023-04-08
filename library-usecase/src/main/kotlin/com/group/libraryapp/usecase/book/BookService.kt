package com.group.libraryapp.usecase.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanHistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanHistory.type.UserLoanStatus
import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.dto.book.request.BookReturnRequest
import com.group.libraryapp.dto.book.response.BookStatResponse
import com.group.libraryapp.repository.BookQuerydslRepository
import com.group.libraryapp.security.AuthenticationDTO
import com.group.libraryapp.util.fail
import com.group.libraryapp.util.returnFail
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookService (
    val bookRepository: BookRepository,
    val bookQuerydslRepository: BookQuerydslRepository,
    val userRepository: UserRepository,
    val userLoanHistoryRepository: UserLoanHistoryRepository
){
    @Transactional
    fun saveBook(req: BookRequest): Book {
        return bookRepository.save(Book.create(req.name, req.type, req.publisher, req.stock))
    }

    @Transactional
    fun loan(bookId: Long, authenticationDTO: AuthenticationDTO) {
        val book = bookRepository.findByIdOrNull(bookId) ?: fail()
        val user = userRepository.findByName(authenticationDTO.name) ?: fail()
        user.loanBook(book)
    }

    @Transactional
    fun returnBook(bookId: Long, authenticationDTO: AuthenticationDTO) {
        val user = userRepository.findByName(authenticationDTO.name) ?: fail()
        val isExistLoanBook = userLoanHistoryRepository.existsByBookIdAndStatus(bookId, UserLoanStatus.LOANED)
        when {
            isExistLoanBook -> user.returnBook(bookRepository.findByIdOrNull(bookId) ?: fail())
            else -> returnFail(bookId)
        }
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