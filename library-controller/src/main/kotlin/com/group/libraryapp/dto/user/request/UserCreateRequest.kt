package com.group.libraryapp.dto.user.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class UserCreateRequest(
    @field:NotBlank
    val email: String,
    @field:NotBlank
    val domain: String,
    @field:Size(min = 6, message = "at least 6 characters")
    val password: String,
    val name: String,
    val companyId: Long,
)