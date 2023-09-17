package com.group.libraryapp.controller.search

import com.group.libraryapp.usecase.search.SearchBookUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "프로젝트 관련 API")
@RestController
class SearchBookController(
    private val searchBookUseCase: SearchBookUseCase
) {

    @Operation(summary = "도서 검색")
    @GetMapping("/search/book")
    fun search() {
        searchBookUseCase.get()
    }
}
