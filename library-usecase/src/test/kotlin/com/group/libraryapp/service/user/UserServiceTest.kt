package com.group.libraryapp.service.user

import com.group.libraryapp.EMAIL
import com.group.libraryapp.NAME
import com.group.libraryapp.PASSWORD
import com.group.libraryapp.SEARCH_PAGE
import com.group.libraryapp.SEARCH_PAGE_SIZE
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.book.factory.BookFactory
import com.group.libraryapp.domain.company.CompanyRepository
import com.group.libraryapp.domain.company.factory.CompanyFactory
import com.group.libraryapp.domain.user.Email
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanHistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanHistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanHistory.type.UserLoanStatus
import com.group.libraryapp.usecase.user.dto.command.UpdateUserCommand
import com.group.libraryapp.usecase.user.SearchUserUseCase
import com.group.libraryapp.usecase.user.UserService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest @Autowired constructor(
    private val userService: UserService,
    private val searchUserUseCase: SearchUserUseCase,
    private val userRepository: UserRepository,
    private val userLoanHistoryRepository: UserLoanHistoryRepository,
    private val bookRepository: BookRepository,
    val companyRepository: CompanyRepository
) {
    @Test
    @DisplayName("유저 검색")
    fun getUser() {
        val company = companyRepository.save(CompanyFactory.create())
        val users = listOf(
            User(Email(EMAIL), PASSWORD, NAME, company = company),
            User(Email("didwodn8822@gmail.com"), PASSWORD, "재우2", company = company)
        )
        userRepository.saveAll(users)

        val searchUsers = searchUserUseCase.searchUsers(company.id!!, SEARCH_PAGE, SEARCH_PAGE_SIZE)

        Assertions.assertThat(searchUsers.elements).hasSize(2)
        Assertions.assertThat(searchUsers.elements).extracting("name").containsExactlyInAnyOrder(NAME, "재우2")
    }

    @Test
    @DisplayName("유저 업데이트")
    fun updateUser() {
        val saveUser = userRepository.save(User(Email(EMAIL), PASSWORD, NAME))

        userService.updateUserName(UpdateUserCommand(saveUser.id!!, "재우2"))

        val user = userRepository.findAll()
        Assertions.assertThat(user[0].name).isEqualTo("재우2")
    }

    @Test
    fun `유저 대출 히스토리 조회`() {
        val user = userRepository.save(User(Email(EMAIL), PASSWORD, NAME))
        val book1 = BookFactory.create("book-1")
        val book2 = BookFactory.create("book-2")
        val book3 = BookFactory.create("book-3")

        bookRepository.saveAll(listOf(book1, book2, book3))
        userLoanHistoryRepository.saveAll(
            listOf(
                UserLoanHistory.create(user, book1, UserLoanStatus.LOANED),
                UserLoanHistory.create(user, book2, UserLoanStatus.LOANED),
                UserLoanHistory.create(user, book3, UserLoanStatus.RETURNED)
            )
        )

        val results = userService.searchUserLoanHistories()

        Assertions.assertThat(results).hasSize(1)
        Assertions.assertThat(results[0].email.name()).isEqualTo(EMAIL)
        Assertions.assertThat(results[0].books).hasSize(3)
        Assertions.assertThat(results[0].books).extracting("name")
            .containsExactlyInAnyOrder("book-1", "book-2", "book-3")
    }

    @AfterEach
    fun clean() {
        userRepository.deleteAll()
    }
}
