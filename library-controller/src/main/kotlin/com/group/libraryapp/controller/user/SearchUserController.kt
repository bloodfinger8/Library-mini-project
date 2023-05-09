package com.group.libraryapp.controller.user

import com.group.libraryapp.dto.response.BaseResponse
import com.group.libraryapp.dto.response.SuccessRes
import com.group.libraryapp.security.AuthenticationDTO
import com.group.libraryapp.usecase.user.SearchUserUseCase
import com.group.libraryapp.util.UserRole
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "회원 관련 API")
@RestController
class SearchUserController (
    val useCase: SearchUserUseCase,
){
    @Operation(summary = "사용자 검색")
    @Secured(UserRole.ROLE_USER)
    @GetMapping("/user")
    fun getUsers(@Parameter(hidden = true) @AuthenticationPrincipal authenticationDTO: AuthenticationDTO,
                 @RequestParam(value = "page", defaultValue = "0") page: Int,
                 @RequestParam(value = "pageSize", defaultValue = "40") pageSize: Int
    ): ResponseEntity<BaseResponse> {
        return ResponseEntity.ok(SuccessRes(useCase.searchUsers(authenticationDTO.companyId, page, pageSize)))
    }
}