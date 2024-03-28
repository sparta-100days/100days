package com.example.days.domain.oauth.model

import com.example.days.domain.user.model.User
import jakarta.persistence.*

@Entity
@Table(name = "social_login_users")
class SocialLoginUser (

    @JoinColumn(name = "provider_id")
    val providerId: String,

    @Column(name = "provider")
    val provider: String,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

) {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var socialId: Long? = null
}