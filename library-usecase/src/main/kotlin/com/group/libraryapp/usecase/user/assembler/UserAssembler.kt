package com.group.libraryapp.usecase.user.assembler

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.usecase.user.dto.response.UserDto
import org.springframework.stereotype.Component

@Component
class UserAssembler {
    fun toDtoList(users: List<User>): List<UserDto> {
        return users.map { UserDto.from(it) }
    }
}
