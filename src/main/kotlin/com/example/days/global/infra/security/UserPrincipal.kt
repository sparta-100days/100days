package com.example.days.global.infra.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

data class UserPrincipal(
    val subject: Long,
    val email: String,
    val role: Collection<GrantedAuthority>
) {
    constructor(subject: Long, email: String, role: Set<String>): this(
        subject,
        email,
        role.map { SimpleGrantedAuthority("ROLE_$it") }
    )
}