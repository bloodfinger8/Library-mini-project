package com.group.libraryapp.controller.book

import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.security.JWTAccessToken
import com.group.libraryapp.security.JWTTokenProvider
import com.group.libraryapp.type.book.BookType
import com.group.libraryapp.type.user.UserType
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestBookController(
    private val client: WebTestClient,
    private val tokenProvider: JWTTokenProvider
) : FunSpec({
    lateinit var token: String

    beforeTest {
        token =
            tokenProvider.signAcToken(JWTAccessToken.of(1, "didwodn8822@gmail.com", "양재우", UserType.USER, 10))
    }

    test("도서 등록 API 테스트") {
        val req = BookRequest("단위테스트", "A출판사", 1, BookType.COMPUTER, "6F")
        val result = client.registerBook(req, token)
        result.name shouldBe "단위테스트"
    }

    test("도서 대여 API 테스트") {
        val result = client.loanBook(1L, token)
        result shouldBe 20000
    }
})
