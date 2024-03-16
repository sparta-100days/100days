package com.example.days.global.common.exception.dto

import java.time.LocalDateTime
import java.time.LocalDateTime.now

data class ErrorResponse(
    val timestamp: LocalDateTime,
    val error: String?,
    val message: String?
){
    constructor(message: String?): this(now(),null, message)
    constructor(error: String, message: String): this(now(), error, message)
}