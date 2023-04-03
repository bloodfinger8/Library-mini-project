package com.group.libraryapp.controller.user

import com.group.libraryapp.dto.response.SuccessRes
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserSignInRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import com.group.libraryapp.dto.user.response.UserLoanHistoryResponse
import com.group.libraryapp.dto.user.response.UserResponse
import com.group.libraryapp.dto.user.response.UserSignInResponse
import com.group.libraryapp.usecase.user.UserService
import com.group.libraryapp.usecase.user.UserSignInUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Tag(name = "회원 관련 API")
@RestController
class UserController(
    val userService: UserService,
    val signInUseCase: UserSignInUseCase,
){

    @Operation(summary = "사용자 회원가입")
    @ApiResponses(
        ApiResponse(responseCode = "40000", description = ""),
    )
    @PostMapping("/user/sign-up")
    fun signUpUser(@Valid @RequestBody request: UserCreateRequest): ResponseEntity<SuccessRes<Any>> {
        userService.signUp(request)
        return ResponseEntity.ok(SuccessRes<Any>());
    }

    @Operation(summary = "사용자 로그인")
    @ApiResponses(
        ApiResponse(responseCode = "40200", description = "id/pw not matched"),
    )
    @PostMapping("/user/sign-in")
    fun signInUser(@Valid @RequestBody request: UserSignInRequest): ResponseEntity<SuccessRes<UserSignInResponse>> {
        return ResponseEntity.ok(SuccessRes(signInUseCase.signIn(request)))
    }

    @Operation(summary = "사용자 검색")
    @GetMapping("/user")
    fun getUsers(): List<UserResponse> {
        return userService.searchUsers()
    }

    @Operation(summary = "사용자 수정")
    @PutMapping("/user")
    fun updateUserName(@RequestBody request: UserUpdateRequest) {
        userService.updateUserName(request)
    }

    @Operation(summary = "사용자 삭제")
    @DeleteMapping("/user")
    fun deleteUser(@RequestParam name: String) {
        userService.deleteUser(name)

    }

    @Operation(summary = "사용자 도서 렌탈 히스토리 조회")
    @GetMapping("/user/loan")
    fun getLoanHistories() : List<UserLoanHistoryResponse> {
        return userService.searchUserLoanHistories()
    }
}