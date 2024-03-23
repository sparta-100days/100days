package com.example.days.domain.oauth.client.oauth2

import com.example.days.domain.oauth.client.oauth2.dto.OAuth2UserInfo
import com.example.days.domain.oauth.model.OAuth2Provider
import org.springframework.stereotype.Component

@Component
class OAuth2ClientService(
    private val clients: List<OAuth2Client>
) {

    fun socialLogin(provider: OAuth2Provider, authorizationCode: String): OAuth2UserInfo {
        val client: OAuth2Client = this.selectClient(provider)
        val accessToken = client.getAccessToken(authorizationCode)
        return client.retrieveUserInfo(accessToken)
    }

    fun generateLoginPageUrl(provider: OAuth2Provider): String {
        val client: OAuth2Client = this.selectClient(provider)
        return client.generateLoginPageUrl()
    }

    private fun selectClient(provider: OAuth2Provider): OAuth2Client {
        return clients.find { it.supports(provider) }
            ?: throw RuntimeException("지원하지 않는 OAuth Provider 입니다.")
    }
}