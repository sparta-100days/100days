package com.example.days.domain.user.dto.response

data class LoginResponse(
    val accessToken: String,
    val nickname: String,
    val message: String
)