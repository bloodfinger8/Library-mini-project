package com.group.libraryapp.dto.user.response

import com.group.libraryapp.domain.user.User
import org.springframework.data.domain.Page

data class UserResponse(
    val users: List<UserInfo>,
    val hasNext: Boolean,
) {
    companion object {
        fun of(user: Page<User>): UserResponse {
            return UserResponse(
                users = user.content.map { userInfo ->
                    UserInfo(
                        id = userInfo.id!!,
                        email = userInfo.email.name(),
                        name = userInfo.name
                    )
                }.toList(),
                hasNext = user.hasNext()
            )
        }
    }
}

data class UserInfo(
    val id: Long,
    val email: String,
    val name: String,
)
