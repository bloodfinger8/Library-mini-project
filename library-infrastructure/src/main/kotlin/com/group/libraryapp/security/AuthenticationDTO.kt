package com.group.libraryapp.security

import com.group.libraryapp.domain.user.UserType

class AuthenticationDTO(
    val id: Long,
    val email: String,
    val name: String,
    val userType: UserType,
    val companyId: Long
) {
    companion object {
        fun of(id: Long,
               email: String,
               name: String,
               userType: UserType = UserType.USER,
               companyId: Long): AuthenticationDTO {
            return AuthenticationDTO(id, email, name, userType, companyId)
        }
    }
}