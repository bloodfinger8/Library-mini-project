package com.group.libraryapp.controller.search

import com.group.libraryapp.dto.response.BaseResponse
import com.group.libraryapp.dto.response.SuccessRes
import com.group.libraryapp.security.AuthenticationDTO
import com.group.libraryapp.usecase.search.SearchBookUseCase
import com.group.libraryapp.usecase.search.dto.SearchBookCommand
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

@Tag(name = "도서 검색 API")
@RestController
class SearchBookController(
    private val searchBookUseCase: SearchBookUseCase
) {
    @Operation(summary = "도서 검색")
    @Secured(UserRole.ROLE_USER)
    @GetMapping("/search/book")
    fun search(
        @Parameter(hidden = true) @AuthenticationPrincipal
        auth: AuthenticationDTO,
        @RequestParam(value = "title") title: String,
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "pageSize", defaultValue = "20") pageSize: Int
    ): ResponseEntity<BaseResponse> {
        return ResponseEntity.ok(
            SuccessRes(
                searchBookUseCase.get(SearchBookCommand(title, auth.companyId, page, pageSize))
            )
        )
    }
}
