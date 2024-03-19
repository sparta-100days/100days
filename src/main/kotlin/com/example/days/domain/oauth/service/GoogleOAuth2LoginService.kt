package com.example.days.domain.oauth.service

interface GoogleOAuth2LoginService {
    fun login(authorizationCode: String): String
}