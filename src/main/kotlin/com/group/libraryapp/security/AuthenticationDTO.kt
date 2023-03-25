package com.group.libraryapp.security

import com.group.libraryapp.domain.user.Email
import com.group.libraryapp.domain.user.UserType

class AuthenticationDTO(
    val email: String,
    val name: String,
    val userType: UserType
) {
    companion object {
        fun of(email: String,
               name: String,
               userType: UserType = UserType.USER): AuthenticationDTO {
            return AuthenticationDTO(email,name,userType)
        }
    }
}