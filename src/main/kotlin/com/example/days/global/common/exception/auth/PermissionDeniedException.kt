package com.example.days.global.common.exception.auth

class PermissionDeniedException (
    val errorCode: AuthExceptionCode = AuthExceptionCode.PERMISSION_DENIED
): RuntimeException()