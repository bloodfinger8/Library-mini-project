package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.book.response.BookInventoryResponse
import com.group.libraryapp.security.AuthenticationDTO
import com.group.libraryapp.usecase.book.BookInventoryUseCase
import com.group.libraryapp.usecase.book.BookService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class BookInventoryUseCaseTest @Autowired constructor (
    val bookInventoryUseCase: BookInventoryUseCase,
    val bookService: BookService,
    val bookRepository: BookRepository,
    val userRepository: UserRepository,
){

    @Test
    fun `도서 목록 및 나의 대출 현황 조회`() {
        val user = userRepository.save(User(BookServiceTest.EMAIL, BookServiceTest.PASSWORD, BookServiceTest.NAME))
        val book1 = Book.create("book-1")
        val book2 = Book.create("book-2")
        val book3 = Book.create("book-3")
        val book4 = Book.create("book-4")
        bookRepository.saveAll(listOf(book1, book2, book3, book4))

        val auth = AuthenticationDTO.of(user.id!!,user.email.email!!, user.name)
        bookService.loan(book3.id!!, auth)

        val results = bookInventoryUseCase.inventory(auth)

        Assertions.assertThat(results.bookInfos).hasSize(4)
        Assertions.assertThat(results.bookInfos).extracting("name").containsExactlyInAnyOrder("book-1","book-2","book-3","book-4")
        Assertions.assertThat(getLoanedBook(results, book3).loaned).isEqualTo(true)
    }


    private fun getLoanedBook(results: BookInventoryResponse, book3: Book) = results.bookInfos.first { a -> a.id == book3.id }
}