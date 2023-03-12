package com.group.libraryapp.usecase.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanHistory.type.UserLoanStatus
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import com.group.libraryapp.dto.user.response.BookLoanHistoryResponse
import com.group.libraryapp.dto.user.response.UserLoanHistoryResponse
import com.group.libraryapp.dto.user.response.UserResponse
import com.group.libraryapp.repository.UserQuerydslRepository
import com.group.libraryapp.util.fail
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService constructor(
    val userRepository: UserRepository,
    val userQuerydslRepository: UserQuerydslRepository
) {

    @Transactional
    fun signUp(req: UserCreateRequest): User {
        val user = User.create(req.email, req.password, req.name)

        return userRepository.save(user)
    }

    @Transactional(readOnly= true)
    fun searchUsers(): List<UserResponse> {
        return userRepository.findAll().map { user -> UserResponse.of(user) }
    }

    @Transactional
    fun updateUserName(req: UserUpdateRequest) {
        val user = userRepository.findByIdOrNull(req.id) ?: fail()
        user.updateName(req.name)
    }

    @Transactional
    fun deleteUser(name: String) {
        val user = userRepository.findByName(name) ?: fail()
        userRepository.delete(user)
    }

    @Transactional(readOnly = true)
    fun searchUserLoanHistories(): List<UserLoanHistoryResponse> {
        return userQuerydslRepository.getHistories().map(UserLoanHistoryResponse::of)
    }
}