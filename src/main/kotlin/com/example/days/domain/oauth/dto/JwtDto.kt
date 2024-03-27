package com.example.days.domain.oauth.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class JwtDto(
    val accessToken: String,
    val refreshToken: String
)
