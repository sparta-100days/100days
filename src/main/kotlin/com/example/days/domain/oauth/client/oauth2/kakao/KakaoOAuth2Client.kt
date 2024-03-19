package com.example.days.domain.oauth.client.oauth2.kakao

import com.example.days.domain.oauth.client.oauth2.kakao.dto.KakaoTokenResponse
import com.example.days.domain.oauth.client.oauth2.kakao.dto.KakaoUserInfoResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Component
class KakaoOAuth2Client(
    @Value("\${oauth2.kakao.client_id}") val clientId: String,
//    @Value("\${oauth2.kakao.client_secret}") val clientSecret: String,
    @Value("\${oauth2.kakao.redirect_url}") val redirectUrl: String,
    private val restClient: RestClient
) {

    fun generateLoginPageUrl(): String {
        return StringBuilder(KAKAO_AUTH_BASE_URL)
            .append("/oauth/authorize")
            .append("?client_id=").append(clientId)
            .append("&redirect_uri=").append(redirectUrl)
            .append("&response_type=").append("code")
            .toString()
    }

    fun getAccessToken(authorizationCode: String): String {
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
            .body<KakaoTokenResponse>()
            ?.accessToken
            ?: throw RuntimeException("AccessToken 조회 실패")
    }

    fun retrieveUserInfo(accessToken: String): KakaoUserInfoResponse {
        return restClient.get()
            .uri("$KAKAO_API_BASE_URL/v2/user/me")
            .header("Authorization", "Bearer $accessToken")
            .retrieve()
            .body<KakaoUserInfoResponse>()
            ?: throw RuntimeException("UserInfo 조회 실패")
    }

    companion object {
        private const val KAKAO_AUTH_BASE_URL = "https://kauth.kakao.com"
        private const val KAKAO_API_BASE_URL = "https://kapi.kakao.com"
    }
 }