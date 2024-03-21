package com.example.days.domain.oauth.model

import jakarta.persistence.*

@Entity
@Table(name = "social_users")
class SocialUser(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "social_user_id")
    var id: Long? = null,

    @Enumerated(EnumType.STRING)
    val provider: OAuth2Provider,
    val providerId: String,
    val nickname: String,
    val email: String

)