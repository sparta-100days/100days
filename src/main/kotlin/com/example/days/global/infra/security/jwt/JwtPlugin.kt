package com.example.days.global.infra.security.jwt

import com.example.days.domain.user.model.UserRole
import com.example.days.global.infra.redis.RedisUtil
import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant
import java.util.*


@Component
class JwtPlugin(
    @Value("\${auth.jwt.issuer}") private val issuer: String,
    @Value("\${auth.jwt.secret}") private val secret: String,
    @Value("\${auth.jwt.accessTokenExpirationHour}") private val accessTokenExpirationHour: Long,
    @Value("\${auth.jwt.refreshTokenExpirationHour}") private val refreshTokenExpirationHour: Long,
    private val redisUtil: RedisUtil
) {

    val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
    val now = Instant.now()

    fun validateToken(jwt: String): Result<Jws<Claims>> {
        return kotlin.runCatching {
            val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
            Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt)
        }
    }

    fun decodeJwtPayload(oldAccessToken: String): Map<*, *> {
        val objectMapper = ObjectMapper()

        return objectMapper.readValue(
            Base64.getUrlDecoder().decode(oldAccessToken.split(".")[1]).decodeToString(),
            Map::class.java
        )
    }

    fun vaildateRefreshToken(refreshToken: String, oldAccessToken: String) {
        validateToken(refreshToken)
        val subject = decodeJwtPayload(oldAccessToken)["sub"].toString()
        val redisToken = redisUtil.getRefreshToken(subject) ?: throw IllegalArgumentException("refresh token 이 없습니다.")

        validateToken(redisToken)
        if (refreshToken != redisToken) throw IllegalArgumentException("refresh token이 유효하지 않습니다.")
    }

    fun recreateAccessToken(oldAccessToken: String): String {
        val expirationPeriod = Duration.ofHours(accessTokenExpirationHour)
        val payload = decodeJwtPayload(oldAccessToken)

        val claims: Claims = Jwts.claims().apply {
            add(
                mapOf(
                    "id" to payload["id"].toString(),
                    "email" to payload["email"].toString(),
                    "role" to payload["role"].toString()
                )
            )
        }.build()

        return Jwts.builder().subject(payload["sub"].toString())
            .issuer(issuer)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(expirationPeriod)))
            .claims(claims)
            .signWith(key)
            .compact()
    }

    fun accessToken(id: Long, email: String, role: UserRole): String {
        return generateToken(id, email, role, Duration.ofHours(accessTokenExpirationHour))
    }

    fun refreshToken(id: Long, email: String, role: UserRole): String {
        // 기존에 유효한 refreshToken이 존재하는지 여부 확인
        val existRefreshToken = redisUtil.getRefreshToken(id.toString())
        if (existRefreshToken != null) {
            return existRefreshToken
        } else {
            val refreshToken = generateToken(id, email, role, Duration.ofHours(24))
            redisUtil.saveRefreshToken(id.toString(), refreshToken)
            return generateToken(id, email, role, Duration.ofHours(refreshTokenExpirationHour))
        }
    }

    fun generateToken(id: Long, email: String, role: UserRole, expirationPeriod: Duration): String {
        val claims: Claims = Jwts.claims()
            .add(mapOf("email" to email, "role" to role))
            .build()

        return Jwts.builder()
            .header().add("typ", "JWT").and()
            .subject(id.toString())
            .issuer(issuer)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(expirationPeriod)))
            .claims(claims)
            .signWith(key)
            .compact()
    }

}