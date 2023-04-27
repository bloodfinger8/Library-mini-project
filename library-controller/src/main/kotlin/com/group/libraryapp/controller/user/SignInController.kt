package com.group.libraryapp.controller.user

import com.group.libraryapp.dto.response.SuccessRes
import com.group.libraryapp.dto.user.command.SignInCommand
import com.group.libraryapp.dto.user.request.UserSignInRequest
import com.group.libraryapp.dto.user.response.UserSignInResponse
import com.group.libraryapp.usecase.user.SignInUseCase
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
class SignInController (
    val signInUseCase: SignInUseCase,
){
    @Operation(summary = "사용자 로그인")
    @ApiResponses(
        ApiResponse(responseCode = "40200", description = "id/pw not matched"),
        ApiResponse(responseCode = "50000", description = "server error"),
    )
    @PostMapping("/user/sign-in")
    fun signInUser(@Valid @RequestBody request: UserSignInRequest): ResponseEntity<SuccessRes<UserSignInResponse>> {
        return ResponseEntity.ok(SuccessRes(signInUseCase.signIn(SignInCommand(request.email,request.password))))
    }

}