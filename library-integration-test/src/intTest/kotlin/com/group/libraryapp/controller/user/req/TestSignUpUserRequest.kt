package com.group.libraryapp.controller.user.req

import com.group.libraryapp.dto.user.request.UserCreateRequest

class TestSignUpUserRequest {
    companion object {
        fun create(): UserCreateRequest =
            UserCreateRequest("didwodn8822@gmail.com", "123123", "양재우", 10)
    }
}
