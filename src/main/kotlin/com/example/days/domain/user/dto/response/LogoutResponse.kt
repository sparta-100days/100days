package com.example.days.domain.user.dto.response

data class LogoutResponse(
    val deleteToken: String,
    val message: String
)