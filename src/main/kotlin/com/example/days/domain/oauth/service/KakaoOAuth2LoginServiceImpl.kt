package com.example.days.domain.oauth.service

import com.example.days.domain.oauth.client.oauth2.kakao.KakaoOAuth2Client
import com.example.days.domain.oauth.common.JwtHelper
import org.springframework.stereotype.Service

@Service
class KakaoOAuth2LoginServiceImpl(
    private val kakaoOAuth2Client: KakaoOAuth2Client,
    private val socialUserService: SocialUserService,
    private val jwtHelper: JwtHelper
) : KakaoOAuth2LoginService {


    override fun login(authorizationCode: String): String {
        return kakaoOAuth2Client.getAccessToken(authorizationCode)
            .let {
                kakaoOAuth2Client.retrieveUserInfo(it)
            }
            .let {
                socialUserService.registerIfAbsent(it)
            }
            .let {
                jwtHelper.generateAccessToken(it.id!!)
            }
    }
}