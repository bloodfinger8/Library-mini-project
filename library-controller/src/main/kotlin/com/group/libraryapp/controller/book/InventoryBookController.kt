package com.group.libraryapp.controller.book

import com.group.libraryapp.dto.response.BaseResponse
import com.group.libraryapp.dto.response.SuccessRes
import com.group.libraryapp.security.AuthenticationDTO
import com.group.libraryapp.usecase.book.InventoryBookUseCase
import com.group.libraryapp.util.UserRole
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "도서 관련 API")
@RestController
class InventoryBookController (
    val inventoryBookUseCase: InventoryBookUseCase,
){
    @GetMapping("/book")
    @Secured(UserRole.ROLE_USER)
    fun book(@Parameter(hidden = true) @AuthenticationPrincipal authenticationDTO: AuthenticationDTO): ResponseEntity<BaseResponse> {
        return ResponseEntity.ok(SuccessRes(inventoryBookUseCase.inventory(authenticationDTO.id)))
    }
}