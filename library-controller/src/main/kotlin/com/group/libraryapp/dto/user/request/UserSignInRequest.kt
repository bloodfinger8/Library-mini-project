package com.group.libraryapp.dto.user.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UserSignInRequest(
    @field:NotBlank
    val email: String,
    @field:Size(min = 6, message = "at least 6 characters")
    val password: String
)
