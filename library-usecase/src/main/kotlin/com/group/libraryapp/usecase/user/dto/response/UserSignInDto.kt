package com.group.libraryapp.usecase.user.dto.response

import com.group.libraryapp.domain.user.User

data class UserSignInDto(
    val id: Long,
    val accessToken: String,
    val name: String,
) {
    companion object {
        fun of(user: User, accessToken: String): UserSignInDto = UserSignInDto(user.id!!, accessToken, user.name)
    }
}
