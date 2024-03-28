package com.example.days.domain.oauth.dto.google

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class GoogleUserPropertiestResponse (
    val nickname: String,
    val email: String
)