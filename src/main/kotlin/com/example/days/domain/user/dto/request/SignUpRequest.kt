package com.example.days.domain.user.dto.request

import com.example.days.domain.user.model.User
import java.time.LocalDate

data class SignUpRequest(
    val email: String,
    val nickName: String,
    val password: String,
    val birth: LocalDate,
    val status: String = "USER"
)