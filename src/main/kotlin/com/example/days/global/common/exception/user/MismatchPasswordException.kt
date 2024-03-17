package com.example.days.global.common.exception.user

class MismatchPasswordException (
    val errorCode: UserExceptionCode = UserExceptionCode.MISMATCH_PASSWORD_ERROR
): RuntimeException()