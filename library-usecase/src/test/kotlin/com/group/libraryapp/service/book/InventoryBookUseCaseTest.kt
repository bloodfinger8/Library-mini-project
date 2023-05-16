package com.group.libraryapp.service.book

import com.group.libraryapp.EMAIL
import com.group.libraryapp.NAME
import com.group.libraryapp.PASSWORD
import com.group.libraryapp.SEARCH_PAGE
import com.group.libraryapp.SEARCH_PAGE_SIZE
import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.company.Company
import com.group.libraryapp.domain.company.CompanyRepository
import com.group.libraryapp.domain.user.Email
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanHistory.UserLoanHistoryRepository
import com.group.libraryapp.dto.SliceDto
import com.group.libraryapp.dto.book.command.LoanBookCommand
import com.group.libraryapp.dto.book.response.BookDto
import com.group.libraryapp.usecase.book.InventoryBookUseCase
import com.group.libraryapp.usecase.book.LoanBookUseCase
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class InventoryBookUseCaseTest @Autowired constructor(
    val inventoryBookUseCase: InventoryBookUseCase,
    val loanBookUseCase: LoanBookUseCase,
    val bookRepository: BookRepository,
    val userRepository: UserRepository,
    val userLoanHistoryRepository: UserLoanHistoryRepository,
    val companyRepository: CompanyRepository
) {
    @Test
    fun `도서 목록 및 나의 대출 현황 조회`() {
        val company = companyRepository.save(Company.create("company-1"))
        val user = userRepository.save(User(Email(EMAIL), PASSWORD, NAME, company = company))
        val book1 = Book.create("book-1", company = company)
        val book2 = Book.create("book-2", company = company)
        val book3 = Book.create("book-3", company = company)
        val book4 = Book.create("book-4", company = company)
        bookRepository.saveAll(listOf(book1, book2, book3, book4))
        loanBookUseCase.loan(LoanBookCommand(book3.id!!, user.name))

        val results = inventoryBookUseCase.inventory(user.id!!, company.id!!, SEARCH_PAGE, SEARCH_PAGE_SIZE)

        Assertions.assertThat(results.elements).hasSize(4)
        Assertions.assertThat(results.elements).extracting("name")
            .containsExactlyInAnyOrder("book-1", "book-2", "book-3", "book-4")
        Assertions.assertThat(getLoanedBook(results, book3).loaned).isEqualTo(true)
    }

    private fun getLoanedBook(results: SliceDto<BookDto>, book3: Book) =
        results.elements.first { it.id == book3.id }

    @AfterEach
    fun clean() {
        userRepository.deleteAll()
        bookRepository.deleteAll()
        userLoanHistoryRepository.deleteAll()
    }
}
