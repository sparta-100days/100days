package com.example.days.domain.oauth.model

import com.example.days.domain.user.model.User
import jakarta.persistence.*

@Entity
@Table(name = "social_users")
class SocialUser(

    @Enumerated(EnumType.STRING)
    @Column(name = "provider")
    val provider: OAuth2Provider,

    @Column(name = "provider_id")
    val providerId: String,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User,

    @Transient
    val email: String = user.email,

    @Transient
    val nickname: String = user.nickname,

    ) {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null


    companion object {
        fun ofKakao(providerId: String, user: User): SocialUser {
            return SocialUser(
                provider = OAuth2Provider.KAKAO,
                providerId = providerId,
                user = user,
                email = user.email,
                nickname = user.nickname
            )
        }

        fun ofGoogle(providerId: String, user: User): SocialUser {
            return SocialUser(
                provider = OAuth2Provider.GOOGLE,
                providerId = providerId,
                user = user,
                email = user.email,
                nickname = user.nickname
            )
        }

        fun ofNaver(providerId: String, user: User): SocialUser {
            return SocialUser(
                provider = OAuth2Provider.NAVER,
                providerId = providerId,
                user = user,
                email = user.email,
                nickname = user.nickname
            )
        }
    }
}
