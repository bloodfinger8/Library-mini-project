package com.group.libraryapp.usecase.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanHistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanHistory.type.UserLoanStatus
import com.group.libraryapp.dto.book.request.BookLoanRequest
import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.dto.book.request.BookReturnRequest
import com.group.libraryapp.dto.book.response.BookStatResponse
import com.group.libraryapp.repository.BookQuerydslRepository
import com.group.libraryapp.security.AuthenticationDTO
import com.group.libraryapp.util.fail
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
        return bookRepository.save(Book.create(req.name, req.type, req.publisher, req.quantity))
    }

    @Transactional
    fun loan(req: BookLoanRequest, authenticationDTO: AuthenticationDTO) {
        val book = bookRepository.findByIdOrNull(req.bookId) ?: fail()
        val user = userRepository.findByName(authenticationDTO.name) ?: fail()
        user.loanBook(book)
    }

    @Transactional
    fun returnBook(req: BookReturnRequest, authenticationDTO: AuthenticationDTO) {
        val user = userRepository.findByName(authenticationDTO.name) ?: fail()
        val book = bookRepository.findByIdOrNull(req.bookId) ?: fail()
        //todo -> 내가 빌린 책을 반납?
        user.returnBook(book)
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