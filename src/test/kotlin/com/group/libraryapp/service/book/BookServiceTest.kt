package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.loanHistory.UserLoanHistory
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.book.type.BookType
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanHistory.UserLoanHistoryRepositroy
import com.group.libraryapp.domain.user.loanHistory.type.UserLoanStatus
import com.group.libraryapp.dto.book.request.BookLoanRequest
import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.dto.book.request.BookReturnRequest
import com.group.libraryapp.dto.book.response.BookStatResponse
import com.group.libraryapp.usecase.book.BookService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.lang.IllegalArgumentException

@SpringBootTest
class BookServiceTest @Autowired constructor(
    private val bookService: BookService,
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository,
    private val userLoanHistoryRepository: UserLoanHistoryRepositroy

){

    @Test
    @DisplayName("책 저장")
    fun saveBook() {
        val bookRequest = BookRequest("클린 아키텍처", BookType.COMPUTER)

        val book = bookService.saveBook(bookRequest)

        Assertions.assertThat(book.name).isEqualTo("클린 아키텍처")
    }

    @Test
    @DisplayName("책 렌탈")
    fun loanBook() {
        val book = bookRepository.save(Book.create("클린 아키텍처"))
        val user = userRepository.save(User("양재우", 30))

        val bookLoanRequest = BookLoanRequest(user.name, book.name)

        bookService.loanBook(bookLoanRequest)

        val loanHistory = userLoanHistoryRepository.findAll()
        Assertions.assertThat(loanHistory[0].bookName).isEqualTo("클린 아키텍처")
        Assertions.assertThat(loanHistory[0].user.id).isEqualTo(user.id)
        Assertions.assertThat(loanHistory[0].status).isEqualTo(UserLoanStatus.LOANED)
    }


    @Test
    @DisplayName("책이 이미 렌탈시 예외처리")
    fun loanBookException() {
        val book = bookRepository.save(Book.create("클린 아키텍처"))
        val user = userRepository.save(User("양재우", 30))
        userLoanHistoryRepository.save(UserLoanHistory.create(user, book.name))
        val bookLoanRequest = BookLoanRequest(user.name, book.name)


        val message = assertThrows<IllegalArgumentException> {
            bookService.loanBook(bookLoanRequest)
        }.message

        Assertions.assertThat(message).isEqualTo("진작 대출되어 있는 책입니다")
    }

    @Test
    @DisplayName("책 반납")
    fun loanBookReturnTest() {
        val book = bookRepository.save(Book.create("클린 아키텍처"))
        val user = userRepository.save(User("양재우", 30))
        userLoanHistoryRepository.save(UserLoanHistory.create(user, book.name))

        val bookReturnRequest = BookReturnRequest(user.name, book.name)

        bookService.returnBook(bookReturnRequest)

        val loanBook = userLoanHistoryRepository.findAll()
        Assertions.assertThat(loanBook).hasSize(1)
        Assertions.assertThat(loanBook[0].status).isEqualTo(UserLoanStatus.RETURNED)

    }

    @Test
    @DisplayName("카테고리별 도서 통계")
    fun bookStatTest() {
        val books = bookRepository.saveAll(
            listOf(
                Book.create("클린 아키텍처1", BookType.COMPUTER),
                Book.create("클린 아키텍처2", BookType.SCIENCE),
                Book.create("클린 아키텍처3", BookType.SCIENCE)
            )
        )

        val result = bookService.getStat()

        Assertions.assertThat(result).hasSize(2)
        assertCount(result , BookType.COMPUTER, 1)
        assertCount(result , BookType.SCIENCE, 2)
    }
    private fun assertCount(result: List<BookStatResponse> , type: BookType , count: Long) {
        Assertions.assertThat(result.first{ it -> it.type == type}.count).isEqualTo(count)
    }


    @AfterEach
    fun clean() {
        bookRepository.deleteAll()
        userRepository.deleteAll()
    }

}