package com.example.days.domain.oauth.client.oauth2.google

import com.example.days.domain.oauth.client.oauth2.google.dto.GoogleTokenResponse
import com.example.days.domain.oauth.client.oauth2.google.dto.GoogleUserInfoResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Component
class GoogleOAuth2Client(
    @Value("\${oauth2.google.client_id}") val clientId: String,
    @Value("\${oauth2.google.client_secret}") val clientSecret: String,
    @Value("\${oauth2.google.redirect_url}") val redirectUrl: String,
    private val restClient: RestClient
) {

    fun generateLoginPageUrl(): String {
        return StringBuilder(GOOGLE_AUTH_BASE_URL)
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
            "client_secret" to clientSecret,
            "code" to authorizationCode,
            "redirect_uri" to redirectUrl
        )
        return restClient.post()
            .uri("$GOOGLE_TOKEN_BASE_URL/token")
//            .uri("$GOOGLE_AUTH_BASE_URL/o/oauth2/v2/auth")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(LinkedMultiValueMap<String, String>().apply { this.setAll(requestData) })
            .retrieve()
            .body<GoogleTokenResponse>()
            ?.accessToken
            ?: throw RuntimeException("AccessToken 조회 실패")
    }

    fun retrieveUserInfo(accessToken: String): GoogleUserInfoResponse {
        return restClient.get()
            .uri("$GOOGLE_API_BASE_URL/userinfo")
            .header("Authorization", "Bearer $accessToken")
            .retrieve()
            .body<GoogleUserInfoResponse>()
            ?: throw RuntimeException("UserInfo 조회 실패")
    }

    companion object {
        private const val GOOGLE_AUTH_BASE_URL = "https://accounts.google.com"
        private const val GOOGLE_API_BASE_URL = "https://www.googleapis.com/oauth2/v2/"
        private const val GOOGLE_TOKEN_BASE_URL = "https://oauth2.googleapis.com"
    }

}