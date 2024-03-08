package com.example.days.domain.user.dto.request

import java.time.LocalDate

data class ModifyInfoRequest(
    val newPassword: String,
    val confirmPassword: String,
    val nickname: String,
    val birth: LocalDate
)