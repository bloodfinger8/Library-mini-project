package com.group.libraryapp.dto.user.response

import com.group.libraryapp.domain.user.User

data class UserSignInResponse (
    val id: Long,
    val accessToken: String,
    val name: String,
){
    companion object {
        fun of(user: User, accessToken: String) : UserSignInResponse {
            return UserSignInResponse(
                    id = user.id!!,
                    accessToken = accessToken,
                    name = user.name
            )
        }
    }
}