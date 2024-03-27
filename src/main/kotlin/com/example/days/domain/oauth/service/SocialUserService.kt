package com.example.days.domain.oauth.service

import com.example.days.domain.oauth.common.OAuth2LoginUserInfo
import com.example.days.domain.user.model.Status
import com.example.days.domain.user.model.User
import com.example.days.domain.user.model.UserRole
import com.example.days.domain.user.repository.UserRepository
import com.example.days.global.infra.regex.RegexFunc
import com.example.days.global.infra.security.PasswordEncoderConfig
import com.example.days.global.support.RandomCode
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class SocialUserService (
    private val userRepository: UserRepository
) {

    fun registerIfAbsent(userInfo: OAuth2LoginUserInfo): User {
        if (!userRepository.existsByProviderAndProviderId(userInfo.provider, userInfo.id)) {
            val random = RandomCode(RegexFunc()).generateRandomCode(10)
            val pass = PasswordEncoderConfig().passwordEncoder().encode(random)

            val user = User(
                email = userInfo.email,
                nickname = userInfo.nickname,
                password = pass,
                birth = LocalDate.now(),
                accountId = RandomCode(RegexFunc()).generateRandomCode(12),
                isDelete = false,
                status = Status.ACTIVE,
                role = UserRole.USER,
                provider = userInfo.provider,
                providerId = userInfo.id
            )
            userRepository.save(user)
        }

        return userRepository.findByProviderAndProviderId(userInfo.provider, userInfo.id)
    }

}