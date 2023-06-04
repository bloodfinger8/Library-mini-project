package com.group.libraryapp.controller.user

import com.group.libraryapp.dto.response.BaseResponse
import com.group.libraryapp.dto.response.SuccessRes
import com.group.libraryapp.dto.user.request.UpdateUserRequest
import com.group.libraryapp.security.AuthenticationDTO
import com.group.libraryapp.usecase.user.ProfileUseCase
import com.group.libraryapp.util.UserRole
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Tag(name = "회원 관련 API")
@RestController
class ProfileController(
    private val useCase: ProfileUseCase
) {
    @Operation(summary = "사용자 프로필 조회")
    @Secured(UserRole.ROLE_USER)
    @GetMapping("/user/profile/{userId}")
    fun getUserProfile(
        @Parameter(hidden = true) @AuthenticationPrincipal
        authenticationDTO: AuthenticationDTO,
        @PathVariable userId: Long
    ): ResponseEntity<BaseResponse> {
        return ResponseEntity.ok(SuccessRes(useCase.get(userId)))
    }

    @Operation(summary = "나의 프로필 수정")
    @Secured(UserRole.ROLE_USER)
    @PutMapping("/user/profile")
    fun updateProfile(
        @Parameter(hidden = true) @AuthenticationPrincipal
        auth: AuthenticationDTO,
        @RequestBody request: UpdateUserRequest
    ): ResponseEntity<BaseResponse> {
        useCase.update(request.toCmd(auth.id))
        return ResponseEntity.ok(SuccessRes<Any>())
    }

    @Operation(summary = "회원 탈퇴")
    @Secured(UserRole.ROLE_USER)
    @PutMapping("/user/profile/leave")
    fun leave(
        @Parameter(hidden = true) @AuthenticationPrincipal
        auth: AuthenticationDTO
    ): ResponseEntity<BaseResponse> {
        return ResponseEntity.ok(SuccessRes(useCase.leave(auth.id)))
    }
}
