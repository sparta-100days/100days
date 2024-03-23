package com.example.days.domain.oauth2.controller

import com.example.days.domain.oauth2.client.KakaoOAuth2Client
import com.example.days.domain.oauth2.service.OAuth2Service
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class OAuth2Controller(
    private val oauth2Service: OAuth2Service,
    private val kakaoOAuth2Client: KakaoOAuth2Client
) {

    // login 페이지로 redirect
    @GetMapping("/oauth2/login/kakao")
    fun redirectLoginPage(response: HttpServletResponse) {
        val loginPageUrl = kakaoOAuth2Client.generateLoginPageUrl()
        response.sendRedirect(loginPageUrl)
    }

    // AuthorizationCode 로 사용자 인증 처리 해주는 api
    @GetMapping("/oauth2/callback/kakao")
    fun callback(@RequestParam(name = "code") authorizationCode: String): String {
        return oauth2Service.login(authorizationCode)
    }
}