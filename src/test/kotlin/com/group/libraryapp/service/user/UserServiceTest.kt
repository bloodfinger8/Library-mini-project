package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanHistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanHistory.UserLoanHistoryRepositroy
import com.group.libraryapp.domain.user.loanHistory.type.UserLoanStatus
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
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
    private val userRepository: UserRepository,
    private val userLoanHistoryRepository: UserLoanHistoryRepositroy,
) {

    @Test
    @DisplayName("유저 생성 테스트")
    fun saveUserTest() {
        val userCreateRequest = UserCreateRequest("양재우", null)

        val user = userService.saveUser(userCreateRequest)

        Assertions.assertThat(user.name).isEqualTo("양재우")
        Assertions.assertThat(user.age).isEqualTo(null)
    }

    @Test
    @DisplayName("유저 검색")
    fun getUser() {
        val users = listOf(
            User("양재우", 12),
            User("마틴파울러", 15)
        )
        userRepository.saveAll(users)

        val searchUsers = userService.searchUsers()

        Assertions.assertThat(searchUsers).hasSize(2)
        Assertions.assertThat(searchUsers).extracting("name").containsExactlyInAnyOrder("양재우","마틴파울러")
    }

    @Test
    @DisplayName("유저 업데이트")
    fun updateUser() {
        val saveUser = userRepository.save(User("양재우", 30))
        val userUpdateRequest = UserUpdateRequest(saveUser.id!!, "마틴파울러")

        userService.updateUserName(userUpdateRequest)

        val user = userRepository.findAll()
        Assertions.assertThat(user[0].name).isEqualTo("마틴파울러")
    }


    @Test
    @DisplayName("유저 삭제")
    fun deleteUser() {
        userRepository.save(User("양재우", 12))

        userService.deleteUser("양재우")

        Assertions.assertThat(userRepository.findByName("양재우")).isNull()
    }


    @Test
    @DisplayName("유저 대출 히스토리 조회")
    fun getLoanHistories() {
        val user = userRepository.save(User("양재우", null))
        userLoanHistoryRepository.saveAll(listOf(
            UserLoanHistory.create(user,"book-1", UserLoanStatus.LOANED),
            UserLoanHistory.create(user,"book-2", UserLoanStatus.LOANED),
            UserLoanHistory.create(user,"book-3", UserLoanStatus.RETURNED)
        ))

        val results = userService.searchUserLoanHistories()

        Assertions.assertThat(results).hasSize(1)
        Assertions.assertThat(results[0].name).isEqualTo("양재우")
        Assertions.assertThat(results[0].books).hasSize(3)
        Assertions.assertThat(results[0].books).extracting("name")
            .containsExactlyInAnyOrder("book-1","book-2","book-3")
    }

    @AfterEach
    fun clean() {
        userRepository.deleteAll()
    }
}