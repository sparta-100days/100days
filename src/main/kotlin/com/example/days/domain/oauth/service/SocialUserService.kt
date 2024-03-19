package com.example.days.domain.oauth.service

import com.example.days.domain.oauth.client.oauth2.google.dto.GoogleUserInfoResponse
import com.example.days.domain.oauth.client.oauth2.kakao.dto.KakaoUserInfoResponse
import com.example.days.domain.oauth.model.OAuth2Provider
import com.example.days.domain.oauth.model.SocialUser
import com.example.days.domain.oauth.repository.SocialUserRepository
import org.springframework.stereotype.Service

@Service
class SocialUserService(
    private val socialUserRepository: SocialUserRepository
) {

    fun registerIfAbsentKakao(userInfo: KakaoUserInfoResponse): SocialUser {
        return if (!socialUserRepository.existsByProviderAndProviderId(OAuth2Provider.KAKAO, userInfo.id.toString())) {
            val socialUser = SocialUser.ofKakao(userInfo.id, userInfo.nickname)
            socialUserRepository.save(socialUser)
        } else {
            socialUserRepository.findByProviderAndProviderId(OAuth2Provider.KAKAO, userInfo.id.toString())
        }
    }

    fun registerIfAbsentGoogle(userInfo: GoogleUserInfoResponse): SocialUser {
        return if (!socialUserRepository.existsByProviderAndProviderId(OAuth2Provider.GOOGLE, userInfo.id.toString())) {
            val socialUser = SocialUser.ofGoogle(userInfo.id, userInfo.nickname)
            socialUserRepository.save(socialUser)
        } else {
            socialUserRepository.findByProviderAndProviderId(OAuth2Provider.GOOGLE, userInfo.id.toString())
        }
    }
}