package com.group.libraryapp.usecase.user

import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.SliceDto
import com.group.libraryapp.usecase.user.assembler.UserAssembler
import com.group.libraryapp.usecase.user.dto.response.UserDto
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SearchUserUseCase(
    private val userRepository: UserRepository,
    private val userAssembler: UserAssembler
) {
    @Transactional(readOnly = true)
    fun searchUsers(companyId: Long, page: Int, pageSize: Int): SliceDto<UserDto> {
        val users = userRepository.findAllByCompanyId(companyId, PageRequest.of(page, pageSize))
        return SliceDto(users.hasNext(), userAssembler.toDtoList(users.content))
    }
}
