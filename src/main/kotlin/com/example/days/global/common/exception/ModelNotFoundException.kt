package com.example.days.global.common.exception


class ModelNotFoundException(
    val modelName: String,
    val modelId: Long,
    val errorCode: CommonExceptionCode = CommonExceptionCode.ENTITY_NOT_FOUND_ERROR
): RuntimeException()