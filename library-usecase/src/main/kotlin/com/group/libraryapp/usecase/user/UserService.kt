package com.group.libraryapp.usecase.user

import com.group.libraryapp.domain.user.Email
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import com.group.libraryapp.dto.user.response.UserLoanHistoryResponse
import com.group.libraryapp.dto.user.response.UserResponse
import com.group.libraryapp.repository.UserQuerydslRepository
import com.group.libraryapp.exception.fail
import com.group.libraryapp.exception.signUpFail
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService constructor(
    val userRepository: UserRepository,
    val userQuerydslRepository: UserQuerydslRepository,
    val passwordEncoder: PasswordEncoder
) {

    @Transactional
    fun signUp(req: UserCreateRequest): User {
        emailDuplicateCheck(req)
        return userRepository.save(User.create(Email(req.email), passwordEncoder.encode(req.password), req.name))
    }

    private fun emailDuplicateCheck(req: UserCreateRequest) {
        if(userRepository.existsByEmail(Email(req.email))) signUpFail(req.email);
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