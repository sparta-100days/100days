package com.example.days.global.common.exception.user

class DuplicateEmailException(
    val email: String,
    val errorCode: UserExceptionCode = UserExceptionCode.DUPLICATE_EMAIL_ERROR
): RuntimeException()