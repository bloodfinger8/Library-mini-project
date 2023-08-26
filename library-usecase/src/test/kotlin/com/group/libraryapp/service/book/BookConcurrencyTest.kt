// package com.group.libraryapp.service.book
//
// import com.group.libraryapp.domain.book.BookRepository
// import com.group.libraryapp.domain.book.factory.BookFactory
// import com.group.libraryapp.domain.user.User
// import com.group.libraryapp.domain.user.UserRepository
// import com.group.libraryapp.gateway.telegram.Notifier
// import com.group.libraryapp.usecase.book.LoanBookUseCase
// import com.group.libraryapp.usecase.book.dto.command.LoanBookCommand
// import org.junit.jupiter.api.AfterEach
// import org.junit.jupiter.api.Assertions
// import org.junit.jupiter.api.Test
// import org.mockito.Mockito
// import org.springframework.beans.factory.annotation.Autowired
// import org.springframework.boot.test.context.SpringBootTest
// import org.springframework.dao.OptimisticLockingFailureException
// import java.util.concurrent.ExecutionException
// import java.util.concurrent.Executors
//
// @SpringBootTest
// class BookConcurrencyTest @Autowired constructor(
//    val bookRepository: BookRepository,
//    val userRepository: UserRepository
// ) {
//
//    @Test
//    fun `도서 선점 대출시 예외 발생 테스트`() {
//        val book = bookRepository.save(BookFactory.create("클린 아키텍처"))
//        val user = userRepository.save(
//            User(
//                RegisterBookUseCaseTest.EMAIL,
//                RegisterBookUseCaseTest.PASSWORD,
//                RegisterBookUseCaseTest.NAME
//            )
//        )
//        val executorService = Executors.newFixedThreadPool(3)
//
//        val future = executorService.submit { loanBookUseCase().loan(LoanBookCommand(book.id!!, user.name)) }
//        val future2 = executorService.submit { loanBookUseCase().loan(LoanBookCommand(book.id!!, user.name)) }
//        val future3 = executorService.submit { loanBookUseCase().loan(LoanBookCommand(book.id!!, user.name)) }
//
//        var result = Exception()
//        try {
//            future.get()
//            future2.get()
//            future3.get()
//        } catch (e: ExecutionException) {
//            result = e.cause as Exception
//        }
//
//        Assertions.assertTrue(result is OptimisticLockingFailureException)
//    }
//
//    fun loanBookUseCase() = LoanBookUseCase(
//        bookRepository,
//        userRepository,
//        Mockito.mock(Notifier::class.java)
//    )
//
//    @AfterEach
//    fun clean() {
//        userRepository.deleteAll()
//        bookRepository.deleteAll()
//    }
// }
