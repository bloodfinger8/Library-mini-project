package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.Email
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanHistory.UserLoanHistoryRepository
import com.group.libraryapp.dto.book.command.LoanBookCommand
import com.group.libraryapp.dto.book.response.BookInventoryResponse
import com.group.libraryapp.usecase.book.InventoryBookUseCase
import com.group.libraryapp.usecase.book.LoanBookUseCase
import com.group.libraryapp.usecase.book.RegisterBookUseCase
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class InventoryBookUseCaseTest @Autowired constructor (
    val inventoryBookUseCase: InventoryBookUseCase,
    val registerBookUseCase: RegisterBookUseCase,
    val loanBookUseCase: LoanBookUseCase,
    val bookRepository: BookRepository,
    val userRepository: UserRepository,
    val userLoanHistoryRepository: UserLoanHistoryRepository,
){
    companion object {
        val EMAIL = Email("test@naver.com")
        const val PASSWORD = "123456"
        const val NAME = "양재우"
    }

    @Test
    fun `도서 목록 및 나의 대출 현황 조회`() {
        val user = userRepository.save(User(EMAIL, PASSWORD, NAME))
        val book1 = Book.create("book-1")
        val book2 = Book.create("book-2")
        val book3 = Book.create("book-3")
        val book4 = Book.create("book-4")
        bookRepository.saveAll(listOf(book1, book2, book3, book4))
        loanBookUseCase.loan(LoanBookCommand(book3.id!!, user.name))

        val results = inventoryBookUseCase.inventory(user.id!!)

        Assertions.assertThat(results.bookInfos).hasSize(4)
        Assertions.assertThat(results.bookInfos).extracting("name").containsExactlyInAnyOrder("book-1","book-2","book-3","book-4")
        Assertions.assertThat(getLoanedBook(results, book3).loaned).isEqualTo(true)
    }

    private fun getLoanedBook(results: BookInventoryResponse, book3: Book) = results.bookInfos.first { a -> a.id == book3.id }


    @AfterEach
    fun clean() {
        userRepository.deleteAll()
        bookRepository.deleteAll()
        userLoanHistoryRepository.deleteAll()
    }
}