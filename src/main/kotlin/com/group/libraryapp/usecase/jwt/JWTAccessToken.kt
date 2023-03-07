package com.group.libraryapp.usecase.jwt

import com.group.libraryapp.domain.user.UserType


class JWTAccessToken constructor(
    email: String,
    name: String,
    userType: UserType
) {
    companion object {
        const val TTL: Int = 1000 * 60 * 60

        fun of(email: String, name: String, userType: UserType): JWTAccessToken {
            return JWTAccessToken(email,name,userType)
        }
    }
}