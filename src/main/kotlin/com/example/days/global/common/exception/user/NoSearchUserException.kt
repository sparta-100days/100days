package com.example.days.global.common.exception.user

class NoSearchUserByEmailException(
    val email: String,
    val errorCode: UserExceptionCode = UserExceptionCode.NO_SEARCH_USER_BY_EMAIL_ERROR
): RuntimeException()