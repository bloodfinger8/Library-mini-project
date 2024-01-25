package com.group.libraryapp.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.group.libraryapp.controller.user.SignInController
import com.group.libraryapp.usecase.user.SignInUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc

@WebMvcTest(controllers = [SignInController::class])
abstract class ControllerInitTestSupporter {
    @Autowired
    open lateinit var mockMvc: MockMvc

    @Autowired
    open lateinit var objectMapper: ObjectMapper

    @MockBean
    open lateinit var signInUseCase: SignInUseCase
}
