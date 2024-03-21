package com.example.days.domain.oauth.client.oauth2.naver.dto

import com.example.days.domain.oauth.client.oauth2.OAuth2LoginUserInfo
import com.example.days.domain.oauth.model.OAuth2Provider
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
class NaverLoginUserInfoResponse(
    id: Long,
    properties: NaverUserPropertiesResponse
) : OAuth2LoginUserInfo(
    provider = OAuth2Provider.NAVER,
    id = id.toString(),
    email = properties.email,
    nickname = properties.nickname
)