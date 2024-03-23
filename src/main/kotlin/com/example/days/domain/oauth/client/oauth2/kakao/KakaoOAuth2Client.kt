package com.example.days.domain.oauth.client.oauth2.kakao

import com.example.days.domain.oauth.client.oauth2.OAuth2Client
import com.example.days.domain.oauth.client.oauth2.dto.OAuth2UserInfo
import com.example.days.domain.oauth.client.oauth2.kakao.dto.KakaoTokenResponse
import com.example.days.domain.oauth.model.OAuth2Provider
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Component
class KakaoOAuth2Client(
    @Value("\${security.oauth2.client.registration.kakao.client_id}") val clientId: String,
    @Value("\${security.oauth2.client.registration.kakao.client_secret}") val clientSecret: String,
    @Value("\${security.oauth2.client.registration.kakao.redirect_uri}") val redirectUrl: String,
    @Value("\${security.oauth2.client.registration.kakao.authorization-grant-type}") val grantType: String,
    private val restClient: RestClient
) : OAuth2Client {

    override fun generateLoginPageUrl(): String {
        return StringBuilder(KAKAO_AUTH_BASE_URL)
            .append("/oauth/authorize")
            .append("?client_id=").append(clientId)
            .append("&redirect_uri=").append(redirectUrl)
            .append("&response_type=").append("code")
            .append("&scope=").append("profile_nickname,account_email")
            .toString()
    }

    override fun getAccessToken(authorizationCode: String): String {
        val requestData = mutableMapOf(
            "grant_type" to grantType,
            "client_id" to clientId,
            "client_secret" to clientSecret,
            "redirect_uri" to redirectUrl,
            "code" to authorizationCode
        )

        return restClient.post()
            .uri("$KAKAO_AUTH_BASE_URL/oauth/token")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(LinkedMultiValueMap<String, String>().apply { this.setAll(requestData) })
            .retrieve()
            .body<KakaoTokenResponse>()
            ?.accessToken
            ?: throw RuntimeException("Kakao AccessToken 조회 실패")
    }

    override fun retrieveUserInfo(accessToken: String): OAuth2UserInfo {
        return restClient.get()
            .uri("$KAKAO_API_BASE_URL/v2/user/me")
            .header("Authorization", "Bearer $accessToken")
            .retrieve()
            .body<OAuth2UserInfo>()
            ?: throw RuntimeException("Kakao UserInfo 조회 실패")
    }

    override fun supports(provider: OAuth2Provider): Boolean {
        return provider == OAuth2Provider.KAKAO
    }

    companion object {
        private const val KAKAO_AUTH_BASE_URL = "https://kauth.kakao.com"
        private const val KAKAO_API_BASE_URL = "https://kapi.kakao.com"
    }
}