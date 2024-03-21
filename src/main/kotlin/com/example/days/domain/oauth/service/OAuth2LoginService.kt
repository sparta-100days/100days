package com.example.days.domain.oauth.service

import com.example.days.domain.oauth.model.OAuth2Provider

interface OAuth2LoginService {
    fun login(provider: OAuth2Provider, authorizationCode: String): String
}