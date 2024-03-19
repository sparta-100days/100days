package com.example.days.domain.oauth.controller

import com.example.days.domain.oauth.client.oauth2.google.GoogleOAuth2Client
import com.example.days.domain.oauth.service.GoogleOAuth2LoginService
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class GoogleOAuth2LoginController(
    private val googleOAuth2LoginService: GoogleOAuth2LoginService,
    private val googleOAuth2Client: GoogleOAuth2Client
) {

    @GetMapping("/oauth2/login/google")
    fun redirectLoginPage(response: HttpServletResponse) {
        val loginPageUrl = googleOAuth2Client.generateLoginPageUrl()
        response.sendRedirect(loginPageUrl)
    }

    @GetMapping("/oauth2/callback/google")
    fun callback(@RequestParam(name = "code") authorizationCode: String): String {
        return googleOAuth2LoginService.login(authorizationCode)
    }
}