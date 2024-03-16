package com.example.days.global.common.exception.user

import org.springframework.http.HttpStatus

enum class UserExceptionCode(
    val httpStatus: HttpStatus,
    val message: String
) {
    DUPLICATE_EMAIL_ERROR(HttpStatus.CONFLICT, "이미 존재하는 이메일 입니다. email => %s"),
    NO_SEARCH_USER_BY_EMAIL(HttpStatus.CONFLICT, "해당 이메일로 가입된 계정이 없습니다. email => %s"),
    MISMATCH_PASSWORD_ERROR(HttpStatus.CONFLICT, "비밀번호가 일치하지 않습니다.")
}