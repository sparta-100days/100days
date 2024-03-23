package com.example.days.domain.oauth.service

import com.example.days.domain.oauth.client.oauth2.dto.OAuth2UserInfo
import com.example.days.domain.user.repository.UserRepository
import com.example.days.global.infra.security.jwt.JwtPlugin
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class OAuth2LoginSuccessHandler(
    private val userRepository: UserRepository,
    private val jwtPlugin: JwtPlugin
): AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val info = authentication.principal as OAuth2UserInfo
        val user = userRepository.findByEmail(info.properties.email)
        val accessToken = jwtPlugin.accessToken(
            id = user.id!!,
            email = user.email,
            role = user.role
        )

        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.write(accessToken)
    }
}