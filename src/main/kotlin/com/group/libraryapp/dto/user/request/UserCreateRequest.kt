package com.group.libraryapp.dto.user.request

import javax.validation.constraints.Max
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class UserCreateRequest(
    @field:NotBlank
    val email: String,
    @field:Size(min = 6, message = "6자 이상 입력하세요")
    val password: String,
    val name: String,
)