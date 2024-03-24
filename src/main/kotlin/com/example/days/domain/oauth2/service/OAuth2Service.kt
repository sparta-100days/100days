package com.example.days.domain.oauth2.service

import com.example.days.domain.oauth2.model.OAuth2Provider
import com.example.days.domain.user.service.UserService
import com.example.days.global.infra.security.jwt.JwtPlugin
import org.springframework.stereotype.Service

@Service
class OAuth2Service(
    private val oauth2ClientService: OAuth2ClientService,
    private val userService: UserService,
    private val jwtPlugin: JwtPlugin
) {

    fun login(provider: OAuth2Provider, authorizationCode: String): String {
        return oauth2ClientService.login(provider, authorizationCode)
            .let { userService.registerIfAbsent(provider, it) }
            .let { jwtPlugin.accessToken(it.id!!, it.email, it.role) }
    }
}