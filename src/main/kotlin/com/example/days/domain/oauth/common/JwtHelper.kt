package com.example.days.domain.oauth.common

import org.springframework.stereotype.Component

@Component
class JwtHelper {

    fun generateAccessToken(id: Long, nickname: String, email: String): String {
        return "SampleAccessToken $id/ $nickname/ $email"
    }
}