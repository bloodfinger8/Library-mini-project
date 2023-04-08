package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.loanHistory.UserLoanHistory
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.book.type.BookType
import com.group.libraryapp.domain.user.Email
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanHistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanHistory.type.UserLoanStatus
import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.dto.book.response.BookStatResponse
import com.group.libraryapp.exception.NotExistStockException
import com.group.libraryapp.security.AuthenticationDTO
import com.group.libraryapp.usecase.book.BookService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BookServiceTest @Autowired constructor(
    private val bookService: BookService,
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository,
    private val userLoanHistoryRepository: UserLoanHistoryRepository
){
    companion object {
        val EMAIL = Email("didwodn82@naver.com")
        const val PASSWORD = "123456"
        const val NAME = "재우"
    }

    @Test
    fun `책 저장`() {
        val bookRequest = BookRequest("클린 아키텍처", "출판사" , 1 , BookType.COMPUTER)

        val book = bookService.saveBook(bookRequest)

        Assertions.assertThat(book.name).isEqualTo("클린 아키텍처")
    }

    @Test
    fun `책 대여`() {
        val book = bookRepository.save(Book.create("클린 아키텍처"))
        val user = userRepository.save(User(EMAIL, PASSWORD, NAME))

        val auth = AuthenticationDTO.of(user.id!!,user.email.email!!, user.name)

        bookService.loan(book.id!!, auth)

        val loanHistory = userLoanHistoryRepository.findAll()
        Assertions.assertThat(loanHistory.first().book.name).isEqualTo("클린 아키텍처")
        Assertions.assertThat(loanHistory.first().user.id).isEqualTo(user.id)
        Assertions.assertThat(loanHistory.first().status).isEqualTo(UserLoanStatus.LOANED)
    }


    @Test
    fun `책의 재고(0개) 부족시 예외발생 체크`() {
        val book = bookRepository.save(Book.create("클린 아키텍처",BookType.COMPUTER,null,0,1))
        val user = userRepository.save(User(EMAIL, PASSWORD, NAME))

        val auth = AuthenticationDTO.of(user.id!!,user.email.email!!, user.name)

        assertThrows<NotExistStockException> {
            bookService.loan(book.id!!, auth)
        }
    }

    @Test
    fun `사용자의 책 대출 이후 반납처리`() {
        val book = bookRepository.save(Book.create("클린 아키텍처"))
        val user = userRepository.save(User(EMAIL, PASSWORD, NAME))
        user.loanBook(book)
        userLoanHistoryRepository.save(UserLoanHistory.create(user, book))

        val auth = AuthenticationDTO.of(user.id!!, user.email.email!!, user.name)

        bookService.returnBook(book.id!!,auth)

        val loanBook = userLoanHistoryRepository.findAll()
        Assertions.assertThat(loanBook).hasSize(1)
        Assertions.assertThat(loanBook.first().status).isEqualTo(UserLoanStatus.RETURNED)
        Assertions.assertThat(loanBook.first().user.id).isEqualTo(user.id)
        Assertions.assertThat(loanBook.first().book.id).isEqualTo(book.id)
    }

    @Test
    fun `카테고리별 도서 통계`() {
        bookRepository.saveAll(
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
        Assertions.assertThat(result.first{ it.type == type}.count).isEqualTo(count)
    }


    @AfterEach
    fun clean() {
        userRepository.deleteAll()
        bookRepository.deleteAll()
        userLoanHistoryRepository.deleteAll()
    }

}