package com.example.days.global.common.exception.auth

import org.springframework.http.HttpStatus

enum class AuthExceptionCode(
    val httpStatus: HttpStatus,
    val message: String
) {
    PERMISSION_DENIED(HttpStatus.FORBIDDEN, "API에 대한 접근 권한이 없습니다.")
}