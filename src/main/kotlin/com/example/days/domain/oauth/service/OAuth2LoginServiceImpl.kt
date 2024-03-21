package com.example.days.domain.oauth.service

import com.example.days.domain.oauth.client.oauth2.OAuth2ClientService
import com.example.days.domain.oauth.common.JwtHelper
import com.example.days.domain.oauth.model.OAuth2Provider
import org.springframework.stereotype.Service

@Service
class OAuth2LoginServiceImpl(
    private val oAuth2ClientService: OAuth2ClientService,
    private val socialUserService: SocialUserService,
    private val jwtHelper: JwtHelper
) : OAuth2LoginService {


    override fun login(provider: OAuth2Provider, authorizationCode: String): String {
        return oAuth2ClientService.login(provider, authorizationCode)
            .let { socialUserService.registerIfAbsent(it) }
            .let { jwtHelper.generateAccessToken(it.id!!) }
    }
}