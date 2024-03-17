package com.example.days.global.common.exception.user

class UserNotFoundException (
    val errorCode: UserExceptionCode = UserExceptionCode.USER_NOT_FOUND_ERROR
): RuntimeException()