package com.example.days.domain.oauth.common

import com.example.days.domain.oauth.model.OAuth2Provider
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
open class OAuth2LoginUserInfo (
    val provider: OAuth2Provider,
    val id: String,
    val nickname: String,
    val email: String
)
