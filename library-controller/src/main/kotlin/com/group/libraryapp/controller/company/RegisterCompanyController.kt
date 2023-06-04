package com.group.libraryapp.controller.company

import com.group.libraryapp.dto.book.request.RegisterCompanyRequest
import com.group.libraryapp.dto.response.BaseResponse
import com.group.libraryapp.dto.response.SuccessRes
import com.group.libraryapp.usecase.book.dto.command.RegisterCompanyCommand
import com.group.libraryapp.usecase.group.RegisterCompanyUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@Tag(name = "회사 관련 API")
@RestController
class RegisterCompanyController(
    val useCase: RegisterCompanyUseCase,
) {
    @Operation(summary = "회사 등록")
    @ApiResponses(
        ApiResponse(responseCode = "40000", description = ""),
    )
    @PostMapping("/company")
    fun registerCompany(
        @Valid @RequestBody request: RegisterCompanyRequest,
    ): ResponseEntity<BaseResponse> {
        useCase.register(RegisterCompanyCommand(request.name, request.domain))
        return ResponseEntity.ok(SuccessRes<Any>())
    }
}
