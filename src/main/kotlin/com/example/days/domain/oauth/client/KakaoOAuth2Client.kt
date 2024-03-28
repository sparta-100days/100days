package com.example.days.domain.oauth.client

import com.example.days.domain.oauth.dto.kakao.KakaoTokenResponse
import com.example.days.domain.oauth.dto.kakao.KakaoUserInfoResponse
import com.example.days.domain.oauth.model.OAuth2Provider
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Component
class KakaoOAuth2Client(
    @Value("\${spring.security.oauth2.client.registration.kakao.client-id}") val clientId: String,
    @Value("\${spring.security.oauth2.client.registration.kakao.client-secret}") val clientSecret: String,
    @Value("\${spring.security.oauth2.client.registration.kakao.redirect-uri}") val redirectUrl: String,
    @Value("\${spring.security.oauth2.client.registration.kakao.scope}") val scope: Set<String>,
    private val restClient: RestClient
) : OAuth2Client {

    override fun generateLoginPageUrl(): String {
        return StringBuilder(KAKAO_AUTH_BASE_URL)
            .append("/oauth/authorize")
            .append("?client_id=").append(clientId)
            .append("&redirect_uri=").append(redirectUrl)
            .append("&response_type=").append("code")
            .append("&scope=").append(scope)
            .toString()
    }

    override fun getAccessToken(authorizationCode: String): String {
        val requestData = mutableMapOf(
            "grant_type" to "authorization_code",
            "client_id" to clientId,
            "client_secret" to clientSecret,
            "redirect_uri" to redirectUrl,
            "code" to authorizationCode
        )
        return restClient.post()
            .uri("$KAKAO_AUTH_BASE_URL/token")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(LinkedMultiValueMap<String, String>().apply { this.setAll(requestData) })
            .retrieve()
            .body<KakaoTokenResponse>()
            ?.accessToken
            ?: throw RuntimeException("Kakao AccessToken 조회 실패")
    }

    override fun retrieveUserInfo(accessToken: String): KakaoUserInfoResponse {
        return restClient.get()
            .uri("$KAKAO_API_BASE_URL/v2/user/me")
            .header("Authorization", "Bearer $accessToken")
            .retrieve()
            .body<KakaoUserInfoResponse>()
            ?: throw RuntimeException("Kakao UserInfo 조회 실패")
    }

    override fun supports(provider: OAuth2Provider): Boolean {
        return provider == OAuth2Provider.KAKAO
    }

    companion object {
        const val KAKAO_AUTH_BASE_URL = "https://kauth.kakao.com/oauth"
        const val KAKAO_API_BASE_URL = "https://kapi.kakao.com"
    }
}