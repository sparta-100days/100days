package com.example.days.domain.oauth2.client.kakao.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class KakaoUserInfoResponse(
    //   val provider: OAuth2Provider,
    val id: String,
    //  val properties: KakaoUserPropertiesResponse
) {
}