package com.group.libraryapp.dto.user.response

import com.group.libraryapp.domain.user.User

data class SignUpResponse(
    val email: String,
    val name: String,
){
    companion object {
        fun of(user: User): SignUpResponse {
            return SignUpResponse(user.email.name(), user.name)
        }
    }
}
