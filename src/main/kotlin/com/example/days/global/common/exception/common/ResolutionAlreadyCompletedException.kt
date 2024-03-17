package com.example.days.global.common.exception.common

class ResolutionAlreadyCompletedException (
    val errorCode: CommonExceptionCode = CommonExceptionCode.RESOLUTION_ALREADY_COMPLETED_ERROR
): RuntimeException()