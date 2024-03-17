package com.example.days.global.common.exception.common

class CheckAlreadyCompletedException (
    val errorCode: CommonExceptionCode = CommonExceptionCode.CHECK_ALREADY_COMPLETED_ERROR
): RuntimeException()