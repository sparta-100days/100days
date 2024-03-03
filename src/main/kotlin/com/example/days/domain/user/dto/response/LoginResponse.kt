package com.example.days.domain.user.dto.response

import com.example.days.domain.user.model.User
import com.example.days.global.infra.security.jwt.JwtPlugin

data class LoginResponse(
    val accessToken: String,
    val nickName: String,
    val message: String
)