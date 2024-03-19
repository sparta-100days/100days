package com.example.days.domain.oauth.model

import jakarta.persistence.*

@Entity
@Table(name = "social_users")
class SocialUser(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Enumerated(EnumType.STRING)
    val provider: OAuth2Provider,
    val providerId: String,
    val nickname: String,

) {

    companion object {
        fun ofKakao(id: Long, nickname: String): SocialUser {
            return SocialUser(
                provider = OAuth2Provider.KAKAO,
                providerId = id.toString(),
                nickname = nickname
            )
        }

        fun ofGoogle(id: Long, nickname: String): SocialUser {
            return SocialUser(
                provider = OAuth2Provider.GOOGLE,
                providerId = id.toString(),
                nickname = nickname
            )
        }
    }
}

enum class OAuth2Provider {
    KAKAO,
    GOOGLE,
    NAVER
}