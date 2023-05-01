package com.group.libraryapp.dto.user.command

class SignUpCommand (
    val email: String,
    val domain: String,
    val password: String,
    val name: String,
    val companyId: Long,
)