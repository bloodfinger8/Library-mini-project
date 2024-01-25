package com.group.libraryapp.dto.user.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UserSignInRequest(
    @field:NotBlank(message = "email is required")
    @field:Email(message = "email format is not valid")
    val email: String,
    @field:Size(min = 6, message = "at least 6 characters")
    val password: String
)
