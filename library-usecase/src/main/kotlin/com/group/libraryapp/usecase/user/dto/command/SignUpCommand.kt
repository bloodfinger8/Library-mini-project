package com.group.libraryapp.usecase.user.dto.command

data class SignUpCommand(
    val email: String,
    val password: String,
    val name: String,
    val companyId: Long
)
