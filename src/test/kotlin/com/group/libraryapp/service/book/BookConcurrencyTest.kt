package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.book.request.BookLoanRequest
import com.group.libraryapp.security.AuthenticationDTO
import com.group.libraryapp.usecase.book.BookService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.OptimisticLockingFailureException
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors

@SpringBootTest
class BookConcurrencyTest @Autowired constructor(
    val bookService: BookService,
    val bookRepository: BookRepository,
    val userRepository: UserRepository,
) {

    @Test
    fun `도서 선점 대출시 예외 발생 테스트`() {
        val book = bookRepository.save(Book.create("클린 아키텍처"))
        val user = userRepository.save(User(BookServiceTest.EMAIL, BookServiceTest.PASSWORD, BookServiceTest.NAME))
        val executorService = Executors.newFixedThreadPool(3);

        val bookLoanRequest = BookLoanRequest(book.id!!)
        val auth = AuthenticationDTO.of(user.id!!.toInt(), user.email.email!!, user.name)

        val future  = executorService.submit { bookService.loan(bookLoanRequest, auth) }
        val future2 = executorService.submit { bookService.loan(bookLoanRequest, auth) }
        val future3 = executorService.submit { bookService.loan(bookLoanRequest, auth) }

        var result = Exception()
        try {
            future.get()
            future2.get()
            future3.get()
        } catch (e: ExecutionException){
            result = e.cause as Exception
        }

        Assertions.assertTrue(result is OptimisticLockingFailureException);
    }
}