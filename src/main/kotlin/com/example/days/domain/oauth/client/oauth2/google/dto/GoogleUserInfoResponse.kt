package com.example.days.domain.oauth.client.oauth2.google.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class GoogleUserInfoResponse(
    val id: Long,
    val properties: GoogleUserPropertiesResponse
) {

    val nickname: String
        get() = properties.nickname

}