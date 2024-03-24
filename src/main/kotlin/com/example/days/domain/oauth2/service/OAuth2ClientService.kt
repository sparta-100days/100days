package com.example.days.domain.oauth2.service

import com.example.days.domain.oauth2.client.OAuth2Client
import com.example.days.domain.oauth2.client.kakao.dto.KakaoUserInfoResponse
import com.example.days.domain.oauth2.model.OAuth2Provider
import org.springframework.stereotype.Component

@Component
class OAuth2ClientService(
    private val clients: List<OAuth2Client>
) {

    fun login(provider: OAuth2Provider, authorizationCode: String): KakaoUserInfoResponse {
        val client: OAuth2Client = this.selectClient(provider)
        return client.getAccessToken(authorizationCode)
            .let { client.retrieveUserInfo(it) }
    }

    fun generateLoginPageUrl(provider: OAuth2Provider): String {
        val client: OAuth2Client = this.selectClient(provider)
        return client.generateLoginPageUrl()
    }

    private fun selectClient(provider: OAuth2Provider): OAuth2Client {
        return clients.find { it.supports(provider) } ?: throw IllegalArgumentException("지원하지 않는 provider입니다.")
    }
}