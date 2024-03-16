package com.example.days.global.common.exception.common


class ModelNotFoundException(
    val modelName: String,
    val modelId: Long? = null,
    val errorCode: CommonExceptionCode = CommonExceptionCode.ENTITY_NOT_FOUND_ERROR
): RuntimeException()