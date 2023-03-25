package com.group.libraryapp.controller

import com.group.libraryapp.dto.response.BaseResponse
import com.group.libraryapp.dto.response.FailureRes
import com.group.libraryapp.exception.CustomException
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindingResult
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException


@RestControllerAdvice(basePackageClasses = [APIGlobalExceptionHandlerController::class])
class APIGlobalExceptionHandlerController {
    private val logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(value = [CustomException::class])
    fun handleCustomException(ex: CustomException): ResponseEntity<BaseResponse> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FailureRes(50000, ex.message))
    }

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<BaseResponse> {
        val bindingResult: BindingResult = ex.bindingResult
        val builder = StringBuilder()
        for (fieldError in bindingResult.fieldErrors) {
            builder.append("[")
            builder.append(fieldError.field)
            builder.append("](은)는 ")
            builder.append(fieldError.defaultMessage)
            builder.append(", 입력된 값: [")
            builder.append(fieldError.rejectedValue)
            builder.append("].")
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FailureRes(40000, builder.toString()))
    }

    @ExceptionHandler(value = [MethodArgumentTypeMismatchException::class])
    fun handleMethodArgumentTypeMismatchException(e: MethodArgumentTypeMismatchException): ResponseEntity<BaseResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FailureRes(40000, "${e.name} is wrong value. ${e.value}"))
    }

    @ExceptionHandler(value = [HttpMessageNotReadableException::class, IllegalArgumentException::class])
    fun handleHttpMessageNotReadableException(e: RuntimeException): ResponseEntity<BaseResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FailureRes(40000, e.message!!))
    }

//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(value = [Exception::class])
//    fun handleException(e: Exception): FailureRes {
//        logger.error("server error {}", e)
//        return FailureRes(500000, "test")
//    }
}