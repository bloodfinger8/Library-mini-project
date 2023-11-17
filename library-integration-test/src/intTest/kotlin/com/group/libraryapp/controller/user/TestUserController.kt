package com.group.libraryapp.controller.user

import com.group.libraryapp.controller.user.req.TestSignInUserRequest
import com.group.libraryapp.controller.user.req.TestSignUpUserRequest
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestUserController(
    private val client: WebTestClient
) : FunSpec({

    test("사용자 로그인 API 테스트") {
        client.signUpUser(TestSignUpUserRequest.create())
        val result = client.signInUser(TestSignInUserRequest.create())
        result.accessToken shouldNotBe null
    }
})
