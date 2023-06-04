package com.group.libraryapp.usecase.user

import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.exception.userNotFoundFail
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

    fun update() {
    }
}
