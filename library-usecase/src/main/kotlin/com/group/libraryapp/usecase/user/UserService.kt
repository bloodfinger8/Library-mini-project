package com.group.libraryapp.usecase.user

import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.command.UpdateUserCommand
import com.group.libraryapp.dto.user.response.UserLoanHistoryResponse
import com.group.libraryapp.repository.UserQuerydslRepository
import com.group.libraryapp.exception.fail
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService constructor(
    val userRepository: UserRepository,
    val userQuerydslRepository: UserQuerydslRepository
) {
    @Transactional
    fun updateUserName(command: UpdateUserCommand) {
        val user = userRepository.findByIdOrNull(command.id) ?: fail()
        user.updateName(command.name)
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