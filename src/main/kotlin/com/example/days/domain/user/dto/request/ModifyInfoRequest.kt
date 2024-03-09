package com.example.days.domain.user.dto.request

import java.time.LocalDate

data class ModifyInfoRequest(
    val password: String,
    val nickname: String,
    val birth: LocalDate
)