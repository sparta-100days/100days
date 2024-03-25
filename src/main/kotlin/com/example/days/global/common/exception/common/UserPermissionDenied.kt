package com.example.days.global.common.exception.common

class UserPermissionDenied (
    val modelName: String,
    val modelId: Long? = null,
    val errorCode: CommonExceptionCode = CommonExceptionCode.USER_PERMISSION_DENIED
): RuntimeException()