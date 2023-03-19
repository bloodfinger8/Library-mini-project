package com.group.libraryapp.security

import com.group.libraryapp.domain.user.Email
import com.group.libraryapp.domain.user.UserType


class JWTAccessToken constructor(
    val email: Email,
    val name: String,
    val userType: UserType
) {
    companion object {
        const val TTL: Int = 1000 * 60 * 60

        fun of(email: Email,
               name: String,
               userType: UserType = UserType.USER): JWTAccessToken {
            return JWTAccessToken(email,name,userType)
        }
    }
}