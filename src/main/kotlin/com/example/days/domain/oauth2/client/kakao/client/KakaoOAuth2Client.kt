package com.example.days.domain.oauth2.client.kakao.client

import com.example.days.domain.oauth2.client.OAurh2UserInfo
import com.example.days.domain.oauth2.client.OAuth2Client
import com.example.days.domain.oauth2.client.OAuth2TokenResponse
import com.example.days.domain.oauth2.model.OAuth2Provider
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Component
class KakaoOAuth2Client(
    @Value("\${oauth2.kakao.client-id}") val clientId: String,
    @Value("\${oauth2.kakao.redirect-uri}") val redirectUrl: String,
    private val restClient: RestClient
) : OAuth2Client {

    override fun generateLoginPageUrl(): String {
        return StringBuilder(KAKAO_AUTH_BASE_URL)
            .append("/oauth/authorize")
            .append("?client_id=").append(clientId)
            .append("&redirect_uri=").append(redirectUrl)
            .append("&response_type=").append("code")
            .toString()
    }

    override fun getAccessToken(authorizationCode: String): String {
        val requestData = mutableMapOf(
            "grant_type" to "authorization_code",
            "client_id" to clientId,
            "code" to authorizationCode
        )
        return restClient.post()
            .uri("$KAKAO_AUTH_BASE_URL/oauth/token")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(LinkedMultiValueMap<String, String>().apply { this.setAll(requestData) })
            .retrieve()
            .body<OAuth2TokenResponse>()
            ?.accessToken
            ?: throw RuntimeException("Kakao AccessToken 조회 실패")
    }

    override fun retrieveUserInfo(accessToken: String): OAurh2UserInfo {
        return restClient.get()
            .uri("$KAKAO_API_BASE_URL/v2/user/me")
            .header("Authorization", "Bearer $accessToken")
            .retrieve()
            .body<OAurh2UserInfo>()
            ?: throw RuntimeException("Kakao UserInfo 조회 실패")
    }

    override fun supports(provider: OAuth2Provider): Boolean {
        return provider == OAuth2Provider.KAKAO
    }

    companion object {
        const val KAKAO_AUTH_BASE_URL = "https://kauth.kakao.com"
        const val KAKAO_API_BASE_URL = "https://kapi.kakao.com"
    }
}