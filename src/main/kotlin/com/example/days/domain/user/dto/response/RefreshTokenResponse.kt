package com.example.days.domain.user.dto.response

data class RefreshTokenResponse(
    val accessToken: String,
    val refreshToken: String
)