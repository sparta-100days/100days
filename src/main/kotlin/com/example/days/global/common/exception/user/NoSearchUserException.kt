package com.example.days.global.common.exception.user

class NoSearchUserException(
    val email: String,
    val errorCode: UserExceptionCode = UserExceptionCode.NO_SEARCH_USER_BY_EMAIL
): RuntimeException()