package com.example.days.domain.user.dto.request

import java.time.LocalDate

data class UserRequest(
    val email: String,
    val nickName: String,
    val birth: LocalDate,
    val isDelete: Boolean,
    val status: String,
    val role: String
)
