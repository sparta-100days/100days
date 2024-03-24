package com.example.days.domain.oauth2.client.kakao.controller

import com.example.days.domain.oauth2.model.OAuth2Provider
import com.example.days.domain.oauth2.service.OAuth2ClientService
import com.example.days.domain.oauth2.service.OAuth2Service
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class KakaoOAuth2Controller(
    private val oauth2Service: OAuth2Service,
    private val oauth2ClientService: OAuth2ClientService
) {

    // login 페이지로 redirect
    @GetMapping("/oauth2/login/kakao")
    fun redirectLoginPage(response: HttpServletResponse) {
        val loginPageUrl = oauth2ClientService.generateLoginPageUrl(OAuth2Provider.KAKAO)
        response.sendRedirect(loginPageUrl)
    }

    // AuthorizationCode 로 사용자 인증 처리 해주는 api
    @GetMapping("/oauth2/callback/kakao")
    fun callback(@RequestParam(name = "code") authorizationCode: String): String {
        return oauth2Service.login(OAuth2Provider.KAKAO, authorizationCode)
    }
}