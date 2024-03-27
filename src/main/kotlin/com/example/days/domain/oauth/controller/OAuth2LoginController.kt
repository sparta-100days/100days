package com.example.days.domain.oauth.controller

import com.example.days.domain.oauth.model.OAuth2Provider
import com.example.days.domain.oauth.service.OAuth2ClientService
import com.example.days.domain.oauth.service.OAuth2LoginService
import com.example.days.domain.user.dto.response.LoginResponse
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/login/oauth2")
class OAuth2Controller(
    private val oauth2LoginService: OAuth2LoginService,
    private val oauth2ClientService: OAuth2ClientService
) {

    // login 페이지로 redirect
    @GetMapping("/code/{provider}")
    fun redirectLoginPage(
        @PathVariable provider: OAuth2Provider,
        response: HttpServletResponse
    ) {
        val loginPageUrl = oauth2ClientService.generateLoginPageUrl(provider)
        response.sendRedirect(loginPageUrl)
    }

    // AuthorizationCode 로 사용자 인증 처리 해주는 api
    @GetMapping("/callback/{provider}")
    fun callback(
        @PathVariable provider: OAuth2Provider,
        response: HttpServletResponse,
        @RequestParam(name = "code") authorizationCode: String
    ): LoginResponse {
        return oauth2LoginService.login(provider, response, authorizationCode)
    }

}