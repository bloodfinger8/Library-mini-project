package com.group.libraryapp.controller.user

import com.fasterxml.jackson.databind.ObjectMapper
import com.group.libraryapp.controller.ControllerInitTestSupporter
import com.group.libraryapp.dto.user.request.UserSignInRequest
import com.group.libraryapp.usecase.user.SignInUseCase
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class SignInControllerTest: ControllerInitTestSupporter() {
    override var mockMvc: MockMvc
        get() = super.mockMvc
        set(value) {}
    override var objectMapper: ObjectMapper
        get() = super.objectMapper
        set(value) {}
    override var signInUseCase: SignInUseCase
        get() = super.signInUseCase
        set(value) {}

    @Test
    @WithMockUser
    fun signIn() {
        mockMvc.perform(
            post("/user/sign-in")
                .content(objectMapper.writeValueAsString(UserSignInRequest("", "test1234")))
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
        ).andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    @WithMockUser
    fun signIn2() {
        mockMvc.perform(
            post("/user/sign-in")
                .content(objectMapper.writeValueAsString(UserSignInRequest("didwodn", "test1234")))
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
        ).andExpect(MockMvcResultMatchers.status().isBadRequest)
    }
}
