package com.example.days.domain.oauth.service

interface KakaoOAuth2LoginService {
    fun login(authorizationCode: String): String
}