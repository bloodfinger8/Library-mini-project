package com.group.libraryapp.security

import com.group.libraryapp.domain.user.UserType


class JWTAccessToken constructor(
    val email: String,
    val name: String,
    val userType: UserType
) {
    companion object {
        const val TTL: Int = 1000 * 60 * 60

        fun of(email: String, name: String, userType: UserType): JWTAccessToken {
            return JWTAccessToken(email,name,userType)
        }
    }
}