package com.example.days.domain.oauth.dto.google

import com.example.days.domain.oauth.common.OAuth2LoginUserInfo
import com.example.days.domain.oauth.model.OAuth2Provider
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
class GoogleLoginUserInfoResponse(
    id: String,
    myinfo: GoogleUserPropertiestResponse,
) : OAuth2LoginUserInfo(
    provider = OAuth2Provider.GOOGLE,
    id = id,
    nickname = myinfo.nickname,
    email = myinfo.email
)