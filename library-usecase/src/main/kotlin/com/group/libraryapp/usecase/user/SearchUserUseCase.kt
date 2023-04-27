package com.group.libraryapp.usecase.user

import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.response.UserResponse
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SearchUserUseCase (val userRepository: UserRepository, ){
    @Transactional(readOnly= true)
    fun searchUsers(page: Int , pageSize: Int): UserResponse {
        val users = userRepository.findAll(PageRequest.of(page, pageSize))
        return UserResponse.of(users)
    }
}