package com.group.libraryapp.exception

import com.group.libraryapp.dto.response.FailureRes
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.io.IOException
import javax.servlet.http.HttpServletResponse




@RestControllerAdvice
class APIGlobalExceptionHandlerController {

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(ex: CustomException): FailureRes {
        return FailureRes(ex.httpStatus.value(), ex.message)
    }
}