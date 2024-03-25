package com.example.days.global.common.exception.common

class TypeNotFoundException (
    val errorCode: CommonExceptionCode = CommonExceptionCode.TYPE_NOT_FOUND_ERROR
): RuntimeException()