package com.group.libraryapp.usecase.user

import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.SliceDto
import com.group.libraryapp.dto.user.response.UserDto
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SearchUserUseCase(val userRepository: UserRepository) {
    @Transactional(readOnly = true)
    fun searchUsers(companyId: Long, page: Int, pageSize: Int): SliceDto<UserDto> {
        val users = userRepository.findAllByCompanyId(companyId, PageRequest.of(page, pageSize))
        return SliceDto(users.hasNext(), users.content.map { UserDto.from(it) })
    }
}
