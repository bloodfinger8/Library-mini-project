package com.group.libraryapp.usecase.user.dto.command

data class SignUpCommand(
    val email: String,
    val password: String,
    val name: String,
    val companyId: Long,
) {
    companion object {
        fun of(email: String, password: String, name: String, companyId: Long): SignUpCommand {
            return SignUpCommand(email, password, name, companyId)
        }
    }
}
