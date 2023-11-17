package com.group.libraryapp.controller.user.req

import com.group.libraryapp.dto.user.request.UserSignInRequest

class TestSignInUserRequest {
    companion object {
        fun create(): UserSignInRequest =
            UserSignInRequest("didwodn8822@gmail.com", "123123")
    }
}
