package com.example.days.domain.oauth2.client.google.client

import com.example.days.domain.oauth2.client.OAuth2Client
import com.example.days.domain.oauth2.client.kakao.dto.KakaoUserInfoResponse
import com.example.days.domain.oauth2.model.OAuth2Provider
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class GoogleOAuth2Client(
    @Value("\${oauth2.google.client-id}") val clientId: String,
    @Value("\${oauth2.google.redirect-uri}") val redirectUrl: String,
    private val restClient: RestClient
) : OAuth2Client {

    override fun generateLoginPageUrl(): String {
        TODO("Not yet implemented")
    }

    override fun getAccessToken(authorizationCode: String): String {
        TODO("Not yet implemented")
    }

    override fun retrieveUserInfo(accessToken: String): KakaoUserInfoResponse {
        TODO("Not yet implemented")
    }

    override fun supports(provider: OAuth2Provider): Boolean {
        TODO("Not yet implemented")
    }
}