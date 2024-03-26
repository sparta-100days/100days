package com.example.days.global.common.exception.user

import org.springframework.http.HttpStatus

enum class UserExceptionCode(
    val httpStatus: HttpStatus,
    val message: String
) {
    DUPLICATE_EMAIL_ERROR(HttpStatus.CONFLICT, "이미 존재하는 이메일 입니다. email => %s"),
    DUPLICATE_NICKNAME_ERROR(HttpStatus.CONFLICT, "이미 존재하는 닉네임 입니다. nickname => %s"),
    NO_SEARCH_USER_BY_EMAIL_ERROR(HttpStatus.CONFLICT, "해당 이메일로 가입된 계정이 없습니다. email => %s"),
    MISMATCH_PASSWORD_ERROR(HttpStatus.CONFLICT, "비밀번호가 일치하지 않습니다."),
    USER_SUSPENDED_ERROR(HttpStatus.FORBIDDEN,"해당 유저는 활동정지 상태입니다."),
    AUTH_CODE_MISMATCH_ERROR(HttpStatus.BAD_REQUEST, "인증번호가 일치하지 않습니다."),
    INVALID_PASSWORD_ERROR(HttpStatus.CONFLICT, "이전에 사용한 적이 있는 비밀번호 입니다."),
    USER_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다.")

}