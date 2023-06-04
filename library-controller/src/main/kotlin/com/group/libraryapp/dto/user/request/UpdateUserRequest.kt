package com.group.libraryapp.dto.user.request

import com.group.libraryapp.usecase.user.dto.command.UpdateUserCommand

data class UpdateUserRequest(
    val name: String,
    val introduction: String
) {
    fun toCmd(id: Long): UpdateUserCommand {
        return UpdateUserCommand(id, name, introduction)
    }
}
