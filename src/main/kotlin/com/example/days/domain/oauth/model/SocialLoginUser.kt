package com.example.days.domain.oauth.model

import com.example.days.domain.user.model.User
import jakarta.persistence.*

@Entity
@Table(name = "social_login_users")
class SocialLoginUser (

    @Column(name = "kakao_id")
    val kakaoId: String,

    @Column(name = "kakao_email")
    val kakaoEmail: String,

    @Column(name = "kakao_nickname")
    val kakaoNickname: String,

    @Column(name = "google_id")
    val googleId: String,

    @Column(name = "google_email")
    val googleEmail: String,

    @Column(name = "google_nickname")
    val googleNickname: String,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

) {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var socialId: Long? = null
}