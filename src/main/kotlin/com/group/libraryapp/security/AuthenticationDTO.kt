package com.group.libraryapp.security

import com.group.libraryapp.domain.user.UserType

class AuthenticationDTO(
    val id: Int,
    val email: String,
    val name: String,
    val userType: UserType
) {
    companion object {
        fun of(id: Int,
               email: String,
               name: String,
               userType: UserType = UserType.USER): AuthenticationDTO {
            return AuthenticationDTO(id,email,name,userType)
        }
    }
}