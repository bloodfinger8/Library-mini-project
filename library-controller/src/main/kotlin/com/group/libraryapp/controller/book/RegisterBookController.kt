package com.group.libraryapp.controller.book

import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.dto.book.command.RegisterBookCommand
import com.group.libraryapp.dto.book.response.BookStatResponse
import com.group.libraryapp.dto.response.BaseResponse
import com.group.libraryapp.dto.response.SuccessRes
import com.group.libraryapp.security.AuthenticationDTO
import com.group.libraryapp.usecase.book.RegisterBookUseCase
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
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Tag(name = "도서 관련 API")
@RestController
class RegisterBookController (
    val registerBookUseCase: RegisterBookUseCase,
){
    @Operation(summary = "도서 등록", security = [SecurityRequirement(name = "Bearer Token")])
    @ApiResponses(
        ApiResponse(responseCode = "40000", description = ""),
    )
    @Secured(UserRole.ROLE_USER)
    @PostMapping("/book")
    fun saveBook(@Valid @RequestBody request: BookRequest,
                 @Parameter(hidden = true) @AuthenticationPrincipal authenticationDTO: AuthenticationDTO): ResponseEntity<BaseResponse> {
        registerBookUseCase.register(RegisterBookCommand(request.name,request.publisher,request.stock,request.type))
        return ResponseEntity.ok(SuccessRes<Any>())
    }

    @GetMapping("/book/loan")
    fun loanBookCount() : Int {
        return registerBookUseCase.countLoanedBook()
    }

    @GetMapping("/book/stat")
    fun bookStat(): List<BookStatResponse> {
        return registerBookUseCase.getStat()
    }
}