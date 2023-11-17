package com.group.libraryapp.controller.user

import com.group.libraryapp.dto.response.SuccessRes
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserSignInRequest
import com.group.libraryapp.usecase.user.dto.response.UserSignInDto
import org.springframework.core.ParameterizedTypeReference
import org.springframework.test.web.reactive.server.WebTestClient

fun WebTestClient.signUpUser(req: UserCreateRequest) {
    this.post().uri("/user/sign-up")
        .bodyValue(req)
        .exchange()
        .expectStatus().is2xxSuccessful
        .expectBody()
        .returnResult()
}

fun WebTestClient.signInUser(req: UserSignInRequest): UserSignInDto {
    return this.post().uri("/user/sign-in")
        .bodyValue(req)
        .exchange()
        .expectStatus().is2xxSuccessful
        .expectBody(object : ParameterizedTypeReference<SuccessRes<UserSignInDto>>() {})
        .returnResult().responseBody!!.data!!
}
