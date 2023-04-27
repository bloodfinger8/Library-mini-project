package com.group.libraryapp.controller.book

import com.group.libraryapp.dto.response.BaseResponse
import com.group.libraryapp.dto.response.SuccessRes
import com.group.libraryapp.security.AuthenticationDTO
import com.group.libraryapp.usecase.book.InventoryBookUseCase
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

@Tag(name = "도서 관련 API")
@RestController
class InventoryBookController (
    val inventoryBookUseCase: InventoryBookUseCase,
){
    @Operation(summary = "도서 목록 & 대출여부 조회")
    @GetMapping("/book")
    @Secured(UserRole.ROLE_USER)
    fun book(@Parameter(hidden = true) @AuthenticationPrincipal authenticationDTO: AuthenticationDTO,
             @RequestParam(value = "page", defaultValue = "0") page: Int,
             @RequestParam(value = "pageSize", defaultValue = "40") pageSize: Int
    ): ResponseEntity<BaseResponse> {
        return ResponseEntity.ok(SuccessRes(inventoryBookUseCase.inventory(authenticationDTO.id, page, pageSize)))
    }
}