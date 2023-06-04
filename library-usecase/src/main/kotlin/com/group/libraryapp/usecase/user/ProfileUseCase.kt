package com.group.libraryapp.usecase.user

import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.exception.userNotFoundFail
import com.group.libraryapp.usecase.user.dto.command.UpdateUserCommand
import com.group.libraryapp.usecase.user.dto.response.UserDetailDto
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProfileUseCase(
    private val userRepository: UserRepository
) {
    @Transactional(readOnly = true)
    fun get(userId: Long): UserDetailDto {
        val user = userRepository.findByIdOrNull(userId) ?: userNotFoundFail(userId)
        return UserDetailDto.of(user)
    }

    @Transactional
    fun update(cmd: UpdateUserCommand) {
        val user = userRepository.findByIdOrNull(cmd.id) ?: userNotFoundFail(cmd.id)
        user.update(cmd.name, cmd.introduction)
    }

    @Transactional
    fun leave(id: Long) {
        val user = userRepository.findByIdOrNull(id) ?: userNotFoundFail(id)
        TODO("반납 완료되지 않은 도서가 있을경우 탈퇴 불가 작업")
        userRepository.delete(user)
    }
}
