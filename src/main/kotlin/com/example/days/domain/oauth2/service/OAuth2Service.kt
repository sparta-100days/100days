package com.example.days.domain.oauth2.service

import com.example.days.domain.oauth2.client.KakaoOAuth2Client
import com.example.days.domain.user.service.UserService
import com.example.days.global.infra.security.jwt.JwtPlugin
import org.springframework.stereotype.Service

@Service
class OAuth2Service(
    private val kakaoOAuth2Client: KakaoOAuth2Client,
    private val userService: UserService,
    private val jwtPlugin: JwtPlugin
) {

    fun login(authorizationCode: String): String {
        return kakaoOAuth2Client.getAccessToken(authorizationCode)
            .let { kakaoOAuth2Client.retrieveUserInfo(it) }
            .let { userService.registerIfAbsent(it) }
            .let { jwtPlugin.accessToken(it.id!!, it.email, it.role) }

        /*
        // 소셜로그인측 : 인가코드 > 액세스 토큰 발급
        val accessToken = kakaoOAuth2Client.getAccessToken(authorizationCode)
        // 소셜로그인측 : 액세스 토큰으로 사용자정보 조회
        val userInfo = kakaoOAuth2Client.retrieveUserInfo(accessToken)
        // 우리측 : 받아온 정보로 우리쪽 유저 데이터 조회, 없으면 가입
        val socialUser = userService.registerIfAbsent(userInfo)
        // 우리측 : user정보 토대로 우리쪽 액세스 토큰 발급후 응답
        return jwtPlugin.accessToken(socialUser.id!!, socialUser.email, socialUser.role)

         */
    }
}