package com.example.days.domain.user.dto.request

import java.time.LocalDate

data class UserRequest(
    val email: String,
    val nickName: String,
    val password: String,
    val birth: LocalDate,
    val createAt: LocalDate,
    val updateAt: LocalDate,
    val isDelete: Boolean,
    val status: String,
    val role: String
)
