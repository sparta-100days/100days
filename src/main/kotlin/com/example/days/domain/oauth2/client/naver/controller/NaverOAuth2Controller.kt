package com.example.days.domain.oauth2.client.naver.controller

import com.example.days.domain.oauth2.model.OAuth2Provider
import com.example.days.domain.oauth2.service.OAuth2ClientService
import com.example.days.domain.oauth2.service.OAuth2Service
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

// 임시구현, 나중에 추가하게 될 경우 보완 필요할 듯. 테스트 X
@RestController
class NaverOAuth2Controller(
    private val oauth2Service: OAuth2Service,
    private val oauth2ClientService: OAuth2ClientService
) {

    // login 페이지로 redirect
    @GetMapping("/oauth2/login/naver")
    fun redirectLoginPage(response: HttpServletResponse) {
        val loginPageUrl = oauth2ClientService.generateLoginPageUrl(OAuth2Provider.NAVER)
        response.sendRedirect(loginPageUrl)
    }

    // AuthorizationCode 로 사용자 인증 처리 해주는 api
    @GetMapping("/oauth2/callback/naver")
    fun callback(@RequestParam(name = "code") authorizationCode: String): String {
        return oauth2Service.login(OAuth2Provider.NAVER, authorizationCode)
    }

}