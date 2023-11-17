package com.group.libraryapp.dto.user.request

import com.group.libraryapp.usecase.user.dto.command.SignUpCommand
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UserCreateRequest(
    @field:NotBlank
    val email: String,
    @field:Size(min = 6, message = "at least 6 characters")
    val password: String,
    val name: String,
    val companyId: Long
) {
    fun cmd(): SignUpCommand =
        SignUpCommand(email, password, name, companyId)
}
