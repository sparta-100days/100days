package com.example.days.domain.user.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.index.Indexed

@Entity
@Table(name = "tokens")
class RefreshToken(

    @jakarta.persistence.Id @Id
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "refresh_token")
    var refreshToken: String,

    @Indexed
    @Column(name = "access_token")
    var accessToken: String

)