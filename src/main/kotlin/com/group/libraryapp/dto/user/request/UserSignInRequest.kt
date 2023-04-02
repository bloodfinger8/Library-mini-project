package com.group.libraryapp.dto.user.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class UserSignInRequest(
        @field:NotBlank
        val email: String,
        @field:Size(min = 6, message = "at least 6 characters")
        val password: String,
)