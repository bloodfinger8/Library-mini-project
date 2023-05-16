package com.group.libraryapp.dto.user.response

import com.group.libraryapp.domain.user.User

data class SignUpDto(
    val email: String,
    val name: String,
) {
    companion object {
        fun from(user: User): SignUpDto {
            return SignUpDto(user.email.name(), user.name)
        }
    }
}
