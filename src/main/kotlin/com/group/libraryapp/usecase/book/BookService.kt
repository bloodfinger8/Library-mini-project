package com.group.libraryapp.usecase.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanHistory.UserLoanHistoryRepositroy
import com.group.libraryapp.dto.book.request.BookLoanRequest
import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.dto.book.request.BookReturnRequest
import com.group.libraryapp.util.fail
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookService constructor(
    val bookRepository: BookRepository,
    val userRepository: UserRepository,
    val userLoanHistoryRepositroy: UserLoanHistoryRepositroy
){

    @Transactional
    fun saveBook(req: BookRequest): Book {
        val book = Book(req.name)
        return bookRepository.save(book)
    }

    @Transactional
    fun loanBook(req: BookLoanRequest) {
        val book = bookRepository.findByName(req.bookName) ?: fail()
        if (userLoanHistoryRepositroy.findByBookNameAndIsReturn(req.bookName,false) != null){
            throw IllegalArgumentException("진작 대출되어 있는 책입니다")
        }
        val user = userRepository.findByName(req.userName) ?: fail()
        user.loanBook(book)
    }

    @Transactional
    fun returnBook(req: BookReturnRequest) {
        val user = userRepository.findByName(req.userName) ?: fail()
        user.returnBook(req.bookName)
    }
}