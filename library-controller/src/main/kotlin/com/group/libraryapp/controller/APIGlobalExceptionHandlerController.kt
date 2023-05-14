package com.group.libraryapp.controller

import com.group.libraryapp.dto.response.BaseResponse
import com.group.libraryapp.dto.response.FailureRes
import com.group.libraryapp.exception.*
import com.group.libraryapp.exception.badCredentialMessage
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException


@RestControllerAdvice(basePackageClasses = [APIGlobalExceptionHandlerController::class])
class APIGlobalExceptionHandlerController {
    private val logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<BaseResponse> {
        val errorMessage = ex.bindingResult.fieldErrors.joinToString(separator = "") {
            "[${it.field}]is ${it.defaultMessage}, request Value: [${it.rejectedValue}]."
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FailureRes(BAD_REQUEST, errorMessage))
    }

    @ExceptionHandler(value = [MethodArgumentTypeMismatchException::class])
    fun handleMethodArgumentTypeMismatchException(e: MethodArgumentTypeMismatchException): ResponseEntity<BaseResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FailureRes(BAD_REQUEST, "${e.name} is wrong value. ${e.value}"))
    }

    @ExceptionHandler(value = [HttpMessageNotReadableException::class, IllegalArgumentException::class])
    fun handleHttpMessageNotReadableException(e: RuntimeException): ResponseEntity<BaseResponse> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(FailureRes(NOT_FOUND, e.message!!))
    }

    @ExceptionHandler(value = [BadCredentialsException::class])
    fun idpNotMatchedHandlerException(e: BadCredentialsException): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FailureRes(40200, badCredentialMessage))
    }

    @ExceptionHandler(value = [NotExistStockException::class])
    fun notExistStockHandlerException(e: NotExistStockException): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FailureRes(e.code, e.message))
    }

    @ExceptionHandler(value = [EmailAlreadyExistsException::class])
    fun emailAlreadyExistsHandlerException(e: EmailAlreadyExistsException): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FailureRes(e.code, e.message))
    }

    @ExceptionHandler(value = [NotExistLoanBookException::class])
    fun notExistLoanBookHandlerException(e: NotExistLoanBookException): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FailureRes(e.code, e.message))
    }

    @ExceptionHandler(value = [NotExistCompanyException::class])
    fun notExistCompanyHandlerException(e: NotExistCompanyException): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FailureRes(e.code, e.message))
    }

    @ExceptionHandler(value = [InvalidEmailDomainException::class])
    fun invalidEmailDomainHandlerException(e: InvalidEmailDomainException): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FailureRes(e.code, e.message))
    }

    @ExceptionHandler(value = [Exception::class])
    fun handleException(e: Exception): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FailureRes(SERVER_ERROR,e.message!!))
    }
}