package com.example.days.domain.oauth.client.oauth2.dto

import com.example.days.domain.oauth.client.oauth2.kakao.dto.KakaoUserPropertiesResponse
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
class OAuth2UserInfo(
    val id: Long,
    val properties: KakaoUserPropertiesResponse
)