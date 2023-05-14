package com.group.libraryapp.controller.company

import com.group.libraryapp.dto.response.BaseResponse
import com.group.libraryapp.dto.response.SuccessRes
import com.group.libraryapp.usecase.group.ListCompanyUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "회사 관련 API")
@RestController
class ListCompanyController(
    val useCase: ListCompanyUseCase,
) {
    @Operation(summary = "회사 목록")
    @ApiResponses(
        ApiResponse(responseCode = "40000", description = ""),
    )
    @GetMapping("/company")
    fun listCompany(
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "pageSize", defaultValue = "40") pageSize: Int
    ): ResponseEntity<BaseResponse> {
        return ResponseEntity.ok(SuccessRes(useCase.list(page, pageSize)))
    }
}
