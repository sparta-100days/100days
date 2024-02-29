package com.example.days.domain.admin.exception

import com.example.days.domain.admin.exception.dto.BaseResponse
import com.example.days.domain.admin.exception.dto.ErrorResponse
import com.example.days.domain.admin.exception.status.ResultCode
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    // ㅇㅅㅇ 회원가입 시에 정규표현식을 맞게 했는데,, 문제가 무엇이냐면,, 메시지가 안뜬다. 그래서 이 에러 처리 코드를 추가해줬는데,,
    // ㅇㅅㅇ 추가하고 나니까 제대로 에러 메시지 창이 뜨므로 이는 남겨두는 것이 좋을 것 같다.
    @ExceptionHandler(MethodArgumentNotValidException::class)
    protected fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<BaseResponse<Map<String, String>>> {
        val errors = mutableMapOf<String, String>()
        ex.bindingResult.allErrors.forEach { error ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.defaultMessage
            errors[fieldName] = errorMessage ?: "Not Exception Message"
        }
        return ResponseEntity(BaseResponse(ResultCode.ERROR.name, errors, ResultCode.ERROR.msg), HttpStatus.BAD_REQUEST)
    }
    @ExceptionHandler(ModelNotFoundException::class)
    fun handlerModelNotFoundException(e: ModelNotFoundException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse(e.message))
    }
}