package com.group.libraryapp.dto.user.request

import com.group.libraryapp.domain.user.Email

data class UserCreateRequest(
    val email: Email,
    val password: String,
    val name: String,
)