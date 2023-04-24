package com.group.libraryapp.security

import com.group.libraryapp.domain.user.Email
import com.group.libraryapp.domain.user.UserType


class JWTAccessToken(
    val id: Long,
    val email: Email,
    val name: String,
    val userType: UserType
) {
    companion object {
        const val TTL: Int = 1000 * 60 * 60

        fun of(id: Long,
               email: Email,
               name: String,
               userType: UserType = UserType.USER): JWTAccessToken {
            return JWTAccessToken(id,email,name,userType)
        }
    }
}