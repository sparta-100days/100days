package com.example.days.domain.oauth.client.oauth2

import com.example.days.domain.oauth.client.oauth2.dto.OAuth2UserInfo
import com.example.days.domain.oauth.model.OAuth2Provider

interface OAuth2Client {

    fun generateLoginPageUrl(): String
    fun getAccessToken(authorizationCode: String): String
    fun retrieveUserInfo(accessToken: String): OAuth2UserInfo
    fun supports(provider: OAuth2Provider): Boolean

}