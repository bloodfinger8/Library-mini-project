package com.group.libraryapp.security

import com.group.libraryapp.domain.user.Email
import com.group.libraryapp.domain.user.UserType

class JWTAccessToken(
    val id: Long,
    val email: Email,
    val name: String,
    val userType: UserType,
    val companyId: Long
) {
    companion object {
        const val TTL: Int = 1000 * 60 * 60
        fun of(
            id: Long,
            email: Email,
            name: String,
            userType: UserType = UserType.USER,
            companyId: Long
        ): JWTAccessToken {
            return JWTAccessToken(id, email, name, userType, companyId)
        }
    }
}
