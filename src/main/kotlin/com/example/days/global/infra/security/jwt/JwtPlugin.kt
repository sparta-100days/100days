package com.example.days.global.infra.security.jwt

import com.example.days.domain.user.model.Status
import com.example.days.domain.user.model.UserRole
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Header
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
) {

    fun validateToken(jwt: String): Result<Jws<Claims>> {
        return kotlin.runCatching {
            val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
            Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt)
        }
    }


    fun generateAccessToken(id: Long, status: Status, role: UserRole): String {
        return generateToken(id, status, role, Duration.ofHours(accessTokenExpirationHour))
    }


    fun generateToken(id: Long, status: Status, role: UserRole, expirationPeriod: Duration): String {
        val claims: Claims = Jwts.claims()
            .add(mapOf("id" to id, "status" to status, "role" to role))
            .build()

        val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
        val now = Instant.now()

        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // ~@~ Header: "typ"
            .subject(id.toString())
            .issuer(issuer)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(expirationPeriod)))
            .claims(claims)
            .signWith(key)
            .compact()
    }

}