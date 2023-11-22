package com.group.libraryapp.controller.book

import com.group.libraryapp.dto.response.BaseResponse
import com.group.libraryapp.dto.response.SuccessRes
import com.group.libraryapp.security.AuthenticationDTO
import com.group.libraryapp.usecase.book.LoanBookUseCase
import com.group.libraryapp.usecase.book.dto.command.LoanBookCommand
import com.group.libraryapp.util.UserRole
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "도서 관련 API")
@RestController
class LoanBookController(val useCase: LoanBookUseCase) {
    @Operation(summary = "도서 대출", security = [SecurityRequirement(name = "Bearer Token")])
    @ApiResponses(
        ApiResponse(responseCode = "40000", description = "request value error"),
        ApiResponse(responseCode = "40401", description = "not exist stock book")
    )
    @Secured(UserRole.ROLE_USER)
    @PostMapping("/book/loan/{bookId}")
    fun loanBook(
        @PathVariable(name = "bookId") bookId: Long,
        @Parameter(hidden = true) @AuthenticationPrincipal
        authenticationDTO: AuthenticationDTO
    ): ResponseEntity<BaseResponse> {
        useCase.loan(LoanBookCommand(bookId, authenticationDTO.name))
        return ResponseEntity.ok(SuccessRes<Any>())
    }
}
