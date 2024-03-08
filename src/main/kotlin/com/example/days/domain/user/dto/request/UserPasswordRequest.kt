package com.example.days.domain.user.dto.request

data class UserPasswordRequest(
    val password: String,
    val newPassword: String
)
