package com.example.days.domain.admin.dto.response

data class LoginAdminResponse(
    val accessToken: String,
    val nickname: String,
    val message: String
)
