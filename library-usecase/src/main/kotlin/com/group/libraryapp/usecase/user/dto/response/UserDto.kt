package com.group.libraryapp.usecase.user.dto.response

import com.group.libraryapp.domain.user.User

data class UserDto(
    val id: Long,
    val email: String,
    val name: String
) {
    companion object {
        fun from(user: User): UserDto = UserDto(user.id!!, user.email.name(), user.name)
    }
}
