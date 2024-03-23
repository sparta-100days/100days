package com.example.days.domain.oauth.service

import com.example.days.domain.oauth.client.oauth2.OAuth2ClientService
import com.example.days.domain.oauth.model.OAuth2Provider
import com.example.days.domain.user.model.UserRole
import com.example.days.global.infra.security.jwt.JwtPlugin
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.stereotype.Service

@Service
class OAuth2LoginService(
    private val oAuth2ClientService: OAuth2ClientService,
    private val socialUserService: SocialUserService,
    private val jwtPlugin: JwtPlugin
) : DefaultOAuth2UserService() {

    // 여기에 기존 유저 테이블 회원가입
    fun socialLogin(provider: OAuth2Provider, authorizationCode: String, providerId: String): String {
        val social = oAuth2ClientService.socialLogin(provider, authorizationCode)

        return socialUserService.registerIfAbsent(social, providerId)
            .let {  jwtPlugin.accessToken(social.id, social.properties.email, UserRole.USER) }
    }
}