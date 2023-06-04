package com.group.libraryapp.controller.user

import com.group.libraryapp.dto.user.request.UserUpdateRequest
import com.group.libraryapp.usecase.user.UserService
import com.group.libraryapp.usecase.user.dto.command.UpdateUserCommand
import com.group.libraryapp.usecase.user.dto.response.UserLoanHistoryDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "회원 관련 API")
@RestController
class UserController(
    val userService: UserService
) {
    @Operation(summary = "로그인회원 정보 수정")
    @PutMapping("/user")
    fun updateUserName(@RequestBody request: UserUpdateRequest) {
        userService.updateUserName(UpdateUserCommand(request.id, request.name))
    }

    @Operation(summary = "사용자 삭제")
    @DeleteMapping("/user")
    fun deleteUser(@RequestParam name: String) {
        userService.deleteUser(name)
    }

    @Operation(summary = "사용자 도서 렌탈 히스토리 조회")
    @GetMapping("/user/loan")
    fun getLoanHistories(): List<UserLoanHistoryDto> {
        return userService.searchUserLoanHistories()
    }
}
