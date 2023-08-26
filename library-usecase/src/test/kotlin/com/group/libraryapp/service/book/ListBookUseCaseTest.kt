// package com.group.libraryapp.service.book
//
// import com.group.libraryapp.EMAIL
// import com.group.libraryapp.NAME
// import com.group.libraryapp.PASSWORD
// import com.group.libraryapp.SEARCH_PAGE
// import com.group.libraryapp.SEARCH_PAGE_SIZE
// import com.group.libraryapp.SliceDto
// import com.group.libraryapp.domain.book.Book
// import com.group.libraryapp.domain.book.BookRepository
// import com.group.libraryapp.domain.book.factory.BookFactory
// import com.group.libraryapp.domain.company.CompanyRepository
// import com.group.libraryapp.domain.company.factory.CompanyFactory
// import com.group.libraryapp.domain.user.Email
// import com.group.libraryapp.domain.user.User
// import com.group.libraryapp.domain.user.UserRepository
// import com.group.libraryapp.domain.user.loanHistory.UserLoanHistoryRepository
// import com.group.libraryapp.gateway.telegram.Notifier
// import com.group.libraryapp.usecase.book.ListBookUseCase
// import com.group.libraryapp.usecase.book.LoanBookUseCase
// import com.group.libraryapp.usecase.book.dto.command.LoanBookCommand
// import com.group.libraryapp.usecase.book.dto.response.BookDto
// import org.assertj.core.api.Assertions
// import org.junit.jupiter.api.AfterEach
// import org.junit.jupiter.api.Test
// import org.mockito.Mockito
// import org.springframework.beans.factory.annotation.Autowired
// import org.springframework.boot.test.context.SpringBootTest
//
// @SpringBootTest
// internal class ListBookUseCaseTest @Autowired constructor(
//    val inventoryBookUseCase: ListBookUseCase,
//    val bookRepository: BookRepository,
//    val userRepository: UserRepository,
//    val userLoanHistoryRepository: UserLoanHistoryRepository,
//    val companyRepository: CompanyRepository
// ) {
//    @Test
//    fun `도서 목록 및 나의 대출 현황 조회`() {
//        val company = companyRepository.save(CompanyFactory.create("company-1"))
//        val user = userRepository.save(User(Email(EMAIL), PASSWORD, NAME, company = company))
//        val book1 = BookFactory.create("book-1", company = company)
//        val book2 = BookFactory.create("book-2", company = company)
//        val book3 = BookFactory.create("book-3", company = company)
//        val book4 = BookFactory.create("book-4", company = company)
//        bookRepository.saveAll(listOf(book1, book2, book3, book4))
//        loanBookUseCase().loan(LoanBookCommand(book3.id!!, user.name))
//
//        val results = inventoryBookUseCase.inventory(user.id!!, company.id!!, SEARCH_PAGE, SEARCH_PAGE_SIZE)
//
//        Assertions.assertThat(results.elements).hasSize(4)
//        Assertions.assertThat(results.elements).extracting("name")
//            .containsExactlyInAnyOrder("book-1", "book-2", "book-3", "book-4")
//        Assertions.assertThat(getLoanedBook(results, book3).loaned).isEqualTo(true)
//    }
//
//    private fun getLoanedBook(results: SliceDto<BookDto>, book3: Book) =
//        results.elements.first { it.id == book3.id }
//
//    private fun loanBookUseCase() = LoanBookUseCase(
//        bookRepository,
//        userRepository,
//        Mockito.mock(Notifier::class.java)
//    )
//
//    @AfterEach
//    fun clean() {
//        userRepository.deleteAll()
//        bookRepository.deleteAll()
//        userLoanHistoryRepository.deleteAll()
//    }
// }
