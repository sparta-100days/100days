package com.example.days.domain.oauth.service

import com.example.days.domain.oauth.client.oauth2.google.GoogleOAuth2Client
import com.example.days.domain.oauth.common.JwtHelper
import org.springframework.stereotype.Service

@Service
class GoogleOAuth2LoginServiceImpl(
    private val googleOAuth2Client: GoogleOAuth2Client,
    private val socialUserService: SocialUserService,
    private val jwtHelper: JwtHelper
) : GoogleOAuth2LoginService {

    override fun login(authorizationCode: String): String {
        return googleOAuth2Client.getAccessToken(authorizationCode)
            .let {
                googleOAuth2Client.retrieveUserInfo(it)
            }
            .let {
                socialUserService.registerIfAbsentGoogle(it)
            }.let {
                jwtHelper.generateAccessToken(it.id!!)
            }
    }
}