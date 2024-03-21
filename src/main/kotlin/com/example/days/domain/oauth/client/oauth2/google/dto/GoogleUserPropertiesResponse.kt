package com.example.days.domain.oauth.client.oauth2.google.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class GoogleUserPropertiesResponse(
    val email: String,
    val nickname: String
)