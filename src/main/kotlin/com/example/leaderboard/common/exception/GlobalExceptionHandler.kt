package com.example.leaderboard.common.exception

import com.example.leaderboard.common.response.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(e: MethodArgumentNotValidException): ApiResponse<Nothing> {
        val message = e.bindingResult.fieldErrors.joinToString(", ") { it.defaultMessage ?: it.field }
        return ApiResponse.fail(message)
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ApiResponse<Nothing> =
        ApiResponse.fail(e.message ?: "Not found")

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleGeneral(e: Exception): ApiResponse<Nothing> =
        ApiResponse.fail("Internal server error")
}
