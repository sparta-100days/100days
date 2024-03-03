package com.example.days.domain.user.dto.response

data class LoginResponse(
    val accessToken: String,
    val nickName: String,
    val message: String
)