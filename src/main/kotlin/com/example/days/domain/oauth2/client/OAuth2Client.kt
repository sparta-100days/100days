package com.example.days.domain.oauth2.client

import com.example.days.domain.oauth2.model.OAuth2Provider

interface OAuth2Client {
    fun generateLoginPageUrl(): String
    fun getAccessToken(authorizationCode: String): String
    fun retrieveUserInfo(accessToken: String): OAurh2UserInfo
    fun supports(provider: OAuth2Provider): Boolean
}