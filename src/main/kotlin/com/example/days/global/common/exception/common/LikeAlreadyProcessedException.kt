package com.example.days.global.common.exception.common

class LikeAlreadyProcessedException (
    val errorCode: CommonExceptionCode = CommonExceptionCode.LIKE_ALREADY_PROCESSED_ERROR
): RuntimeException()