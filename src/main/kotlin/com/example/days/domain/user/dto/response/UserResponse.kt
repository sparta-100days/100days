package com.example.days.domain.user.dto.response

import java.time.LocalDate

data class UserResponse(
    val id: Long,
    val email: String,
    val nickName: String,
    val birth: LocalDate,
    val isDelete: Boolean,
    val status: String,
    val role: String
)
