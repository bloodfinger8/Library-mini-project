package com.group.libraryapp.dto.user.response

import com.group.libraryapp.domain.user.Email
import com.group.libraryapp.domain.user.User

data class UserResponse(
    val id: Long,
    val email: Email,
    val name: String?
) {

    companion object {
        fun of(user: User) : UserResponse {
            return UserResponse(
                id = user.id!!,
                email = user.email,
                name = user.name
            )
        }
    }
}