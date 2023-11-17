package com.group.libraryapp.controller.book

import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.dto.response.SuccessRes
import com.group.libraryapp.usecase.book.dto.response.RegisterBookDto
import org.springframework.core.ParameterizedTypeReference
import org.springframework.test.web.reactive.server.WebTestClient

fun WebTestClient.loanBook(bookId: Long, token: String): Int {
    return this.post().uri("/book/loan/$bookId")
        .headers { http -> http.setBearerAuth(token) }
        .exchange()
        .expectStatus().is2xxSuccessful
        .expectBody(object : ParameterizedTypeReference<SuccessRes<Any>>() {})
        .returnResult().responseBody!!.code
}

fun WebTestClient.registerBook(req: BookRequest, token: String): RegisterBookDto {
    return this.post().uri("/book")
        .headers { http -> http.setBearerAuth(token) }
        .bodyValue(req)
        .exchange()
        .expectStatus().is2xxSuccessful
        .expectBody(object : ParameterizedTypeReference<SuccessRes<RegisterBookDto>>() {})
        .returnResult().responseBody!!.data!!
}
