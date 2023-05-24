package com.group.libraryapp.usecase.user

import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.usecase.user.dto.command.UpdateUserCommand
import com.group.libraryapp.usecase.user.dto.response.UserLoanHistoryDto
import com.group.libraryapp.exception.userNotFoundFail
import com.group.libraryapp.repository.UserQuerydslRepository
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
        val user = userRepository.findByIdOrNull(command.id) ?: userNotFoundFail(command.id)
        user.updateName(command.name)
    }

    @Transactional
    fun deleteUser(name: String) {
        val user = userRepository.findByName(name) ?: userNotFoundFail(name)
        userRepository.delete(user)
    }

    @Transactional(readOnly = true)
    fun searchUserLoanHistories(): List<UserLoanHistoryDto> {
        return userQuerydslRepository.getHistories().map { UserLoanHistoryDto.from(it) }
    }
}
