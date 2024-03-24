package com.example.days.domain.oauth2.client.naver.client

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


// 임시구현, 나중에 추가하게 될 경우 보완 필요할 듯. 테스트 X
@Component
class NaverOAuth2Client(
    @Value("\${oauth2.naver.client-id}") val clientId: String,
    @Value("\${oauth2.naver.redirect-uri}") val redirectUrl: String,
    private val restClient: RestClient
) : OAuth2Client {

    override fun generateLoginPageUrl(): String {
        return StringBuilder(NAVER_AUTH_BASE_URL)
            .append("/authorize")
            .append("?client_id=").append(clientId)
            .append("&redirect_uri=").append(redirectUrl)
            .append("&response_type=").append("code")
            .toString()
    }

    override fun getAccessToken(authorizationCode: String): String {
        val requestData = mutableMapOf(
            "grant_type" to "authorization_code",
            "client_id" to clientId,
            "redirect_uri" to redirectUrl,
            "code" to authorizationCode
        )
        return restClient.post()
            .uri("$NAVER_AUTH_BASE_URL/token")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(LinkedMultiValueMap<String, String>().apply { this.setAll(requestData) })
            .retrieve()
            .body<OAuth2TokenResponse>()
            ?.accessToken
            ?: throw RuntimeException("Naver AccessToken 조회 실패")
    }

    override fun retrieveUserInfo(accessToken: String): OAurh2UserInfo {
        return restClient.get()
            .uri("$NAVER_API_BASE_URL/userinfo")
            .header("Authorization", "Bearer $accessToken")
            .retrieve()
            .body<OAurh2UserInfo>()
            ?: throw RuntimeException("Naver UserInfo 조회 실패")
    }

    override fun supports(provider: OAuth2Provider): Boolean {
        return provider == OAuth2Provider.NAVER
    }

    companion object {
        const val NAVER_AUTH_BASE_URL = "https://nid.naver.com/oauth2.0"
        const val NAVER_API_BASE_URL = "https://openapi.naver.com/v1"
    }
}