package com.example.days.global.common.exception.user

class InvalidPasswordError (
    val errorCode: UserExceptionCode = UserExceptionCode.INVALID_PASSWORD_ERROR
): RuntimeException()