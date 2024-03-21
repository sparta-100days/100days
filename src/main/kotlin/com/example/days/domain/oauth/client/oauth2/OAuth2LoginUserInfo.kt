package com.example.days.domain.oauth.client.oauth2

import com.example.days.domain.oauth.model.OAuth2Provider

open class OAuth2LoginUserInfo(
    val provider: OAuth2Provider,
    val id: String,
    val email: String,
    val nickname: String,
)