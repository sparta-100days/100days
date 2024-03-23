package com.example.days.domain.oauth2.client.kakao.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class KakaoUserInfoResponse(
    val id: Long,
//    val properties: KakaoUserPropertiesResponse
) {
//    val nickname: String
//        get() = properties.nickname
//    val email: String
//        get() = properties.email
}
