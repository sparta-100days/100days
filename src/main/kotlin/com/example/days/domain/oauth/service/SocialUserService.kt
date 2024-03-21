package com.example.days.domain.oauth.service

import com.example.days.domain.oauth.client.oauth2.OAuth2LoginUserInfo
import com.example.days.domain.oauth.model.SocialUser
import com.example.days.domain.oauth.repository.SocialUserRepository
import org.springframework.stereotype.Service

@Service
class SocialUserService(
    private val socialUserRepository: SocialUserRepository
) {

    // OAuth2LoginUserInfo 를 회원가입 시키는 역할
    fun registerIfAbsent(userInfo: OAuth2LoginUserInfo): SocialUser {
        return if (!socialUserRepository.existsByProviderAndProviderId(userInfo.provider, userInfo.id)) {
            val socialUser = SocialUser(
                provider = userInfo.provider,
                providerId = userInfo.id,
                email = userInfo.email,
                nickname = userInfo.nickname,
            )
            socialUserRepository.save(socialUser)
        } else {
            socialUserRepository.findByProviderAndProviderId(userInfo.provider, userInfo.id)
        }
    }
}