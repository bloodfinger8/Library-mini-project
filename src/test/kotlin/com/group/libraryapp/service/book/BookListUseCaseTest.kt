package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.security.AuthenticationDTO
import com.group.libraryapp.usecase.book.BookListUseCase
import com.group.libraryapp.usecase.book.BookService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class BookListUseCaseTest @Autowired constructor (
    val bookListUseCase: BookListUseCase,
    val bookService: BookService,
    val bookRepository: BookRepository,
    val userRepository: UserRepository,
){

    @Test
    fun `도서 목록 리스트`() {
        val book = bookRepository.save(Book.create("클린 아키텍처"))
        val book2 = bookRepository.save(Book.create("클린 아키텍처2"))
        val user = userRepository.save(User(BookServiceTest.EMAIL, BookServiceTest.PASSWORD, BookServiceTest.NAME))

        val auth = AuthenticationDTO.of(user.id!!,user.email.email!!, user.name)

        bookService.loan(book.id!!, auth)

        val books = bookListUseCase.list(auth)
    }


}