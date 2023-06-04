package com.group.libraryapp.service.book

import com.group.libraryapp.BOOK_LOCATION
import com.group.libraryapp.BOOK_NAME
import com.group.libraryapp.BOOK_PUBLISHER
import com.group.libraryapp.BOOK_STOCK
import com.group.libraryapp.BOOK_TYPE
import com.group.libraryapp.COMPANY_ID
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.book.factory.BookFactory
import com.group.libraryapp.domain.book.type.BookType
import com.group.libraryapp.domain.user.Email
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanHistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanHistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanHistory.type.UserLoanStatus
import com.group.libraryapp.exception.NotExistStockException
import com.group.libraryapp.usecase.book.LoanBookUseCase
import com.group.libraryapp.usecase.book.RegisterBookUseCase
import com.group.libraryapp.usecase.book.ReturnBookUseCase
import com.group.libraryapp.usecase.book.dto.command.LoanBookCommand
import com.group.libraryapp.usecase.book.dto.command.RegisterBookCommand
import com.group.libraryapp.usecase.book.dto.command.ReturnBookCommand
import com.group.libraryapp.usecase.book.dto.response.BookStatDto
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class RegisterBookUseCaseTest @Autowired constructor(
    private val registerBookUseCase: RegisterBookUseCase,
    private val loanBookUseCase: LoanBookUseCase,
    private val returnBookUseCase: ReturnBookUseCase,
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository,
    private val userLoanHistoryRepository: UserLoanHistoryRepository
) {
    companion object {
        val EMAIL = Email("didwodn82@naver.com")
        const val PASSWORD = "123456"
        const val NAME = "재우"
    }

    @Test
    fun `책 저장`() {
        val bookRequest =
            RegisterBookCommand(BOOK_NAME, BOOK_PUBLISHER, BOOK_STOCK, BOOK_TYPE, BOOK_LOCATION, COMPANY_ID)

        val book = registerBookUseCase.register(bookRequest)

        Assertions.assertThat(book.name).isEqualTo(BOOK_NAME)
    }

    @Test
    fun `책 대여`() {
        val book = bookRepository.save(BookFactory.create("클린 아키텍처"))
        val user = userRepository.save(User(EMAIL, PASSWORD, NAME))

        loanBookUseCase.loan(LoanBookCommand(book.id!!, user.name))

        val loanHistory = userLoanHistoryRepository.findAll()
        Assertions.assertThat(loanHistory.first().book.name).isEqualTo("클린 아키텍처")
        Assertions.assertThat(loanHistory.first().user.id).isEqualTo(user.id)
        Assertions.assertThat(loanHistory.first().status).isEqualTo(UserLoanStatus.LOANED)
    }

    @Test
    fun `책의 재고(0개) 부족시 예외발생 체크`() {
        val book = bookRepository.save(BookFactory.create(BOOK_NAME, BookType.COMPUTER, null, 0))
        val user = userRepository.save(User(EMAIL, PASSWORD, NAME))

        assertThrows<NotExistStockException> {
            loanBookUseCase.loan(LoanBookCommand(book.id!!, user.name))
        }
    }

    @Test
    fun `사용자의 책 대출 이후 반납처리`() {
        val book = bookRepository.save(BookFactory.create("클린 아키텍처"))
        val user = userRepository.save(User(EMAIL, PASSWORD, NAME))
        user.loanBook(book)
        userLoanHistoryRepository.save(UserLoanHistory.create(user, book))

        returnBookUseCase.returnBook(ReturnBookCommand(book.id!!, user.name))

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
                BookFactory.create("클린 아키텍처1", BookType.COMPUTER),
                BookFactory.create("클린 아키텍처2", BookType.SCIENCE),
                BookFactory.create("클린 아키텍처3", BookType.SCIENCE)
            )
        )

        val result = registerBookUseCase.getStat()

        Assertions.assertThat(result).hasSize(2)
        assertCount(result, BookType.COMPUTER, 1)
        assertCount(result, BookType.SCIENCE, 2)
    }

    private fun assertCount(result: List<BookStatDto>, type: BookType, count: Long) {
        Assertions.assertThat(result.first { it.type == type }.count).isEqualTo(count)
    }

    @AfterEach
    fun clean() {
        userRepository.deleteAll()
        bookRepository.deleteAll()
        userLoanHistoryRepository.deleteAll()
    }
}
