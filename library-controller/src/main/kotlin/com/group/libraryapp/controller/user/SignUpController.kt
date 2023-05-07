package com.group.libraryapp.controller.user

import com.group.libraryapp.dto.response.SuccessRes
import com.group.libraryapp.dto.user.command.SignUpCommand
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.usecase.user.SignUpUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@Tag(name = "회원 관련 API")
@RestController
class SignUpController (
    val useCase: SignUpUseCase,
){
    @Operation(summary = "사용자 회원가입")
    @ApiResponses(
        ApiResponse(responseCode = "40403", description = "already email exists"),
        ApiResponse(responseCode = "40404", description = "not Existed company"),
        ApiResponse(responseCode = "40405", description = "mismatch email domains"),
        ApiResponse(responseCode = "50000", description = "server error"),
    )
    @PostMapping("/user/sign-up")
    fun signUpUser(@Valid @RequestBody request: UserCreateRequest): ResponseEntity<SuccessRes<Any>> {
        useCase.signUp(SignUpCommand.of(request.email, request.password, request.name, request.companyId))
        return ResponseEntity.ok(SuccessRes())
    }
}