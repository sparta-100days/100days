package com.example.days.domain.oauth.service

import com.example.days.domain.oauth.client.oauth2.dto.OAuth2UserInfo
import com.example.days.domain.oauth.model.OAuth2Provider
import com.example.days.domain.oauth.model.SocialUser
import com.example.days.domain.oauth.repository.SocialUserRepository
import com.example.days.domain.user.repository.UserRepository
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.stereotype.Service

@Service
class SocialUserService(
    private val socialUserRepository: SocialUserRepository,
    private val userRepository: UserRepository
) : DefaultOAuth2UserService() {

    // OAuth2LoginUserInfo 를 회원가입 시키는 역할
    fun registerIfAbsent(userInfo: OAuth2UserInfo, providerId: String): SocialUser {
        val provider = OAuth2Provider.valueOf(userInfo.toString())
        val user = userRepository.findByEmail(userInfo.properties.email)

        return if (!socialUserRepository.existsByProviderAndProviderId(provider, userInfo.id.toString())) {
            when (provider) {
                OAuth2Provider.KAKAO -> {
                    val socialKakao = SocialUser.ofKakao(
                        providerId = providerId,
                        user
                    )
                    socialUserRepository.save(socialKakao)
                }

                OAuth2Provider.GOOGLE -> {
                    val socialGoogle = SocialUser.ofGoogle(
                        providerId = providerId,
                        user
                    )
                    socialUserRepository.save(socialGoogle)
                }

                OAuth2Provider.NAVER -> {
                    val socialNaver = SocialUser.ofNaver(
                        providerId = providerId,
                        user
                    )
                    socialUserRepository.save(socialNaver)
                }
            }
        } else {
            throw IllegalArgumentException("해당 로그인은 지원되지 않습니다.")
        }
    }
}