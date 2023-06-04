package com.group.libraryapp.controller.user

import com.group.libraryapp.usecase.user.UserService
import com.group.libraryapp.usecase.user.dto.response.UserLoanHistoryDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "회원 관련 API")
@RestController
class UserController(
    val userService: UserService
) {
    @Operation(summary = "모든 사용자 도서 대여 히스토리 조회")
    @GetMapping("/user/loan")
    fun getLoanHistories(): List<UserLoanHistoryDto> {
        return userService.searchUserLoanHistories()
    }
}
