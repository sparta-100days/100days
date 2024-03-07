package com.example.days.domain.admin.dto.request

import com.example.days.domain.user.model.UserStatus

data class UserBanRequest(
    val status: UserStatus
)
