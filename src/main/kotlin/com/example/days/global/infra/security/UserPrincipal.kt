package com.example.days.global.infra.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

data class UserPrincipal(
    val id: Long,
    val status: Collection<GrantedAuthority>,
    val authorities: Collection<GrantedAuthority>
) {
    constructor(id: Long, status: Set<String>, role: Set<String>) : this(
        id,
        status.map { SimpleGrantedAuthority("STATUS_$it") },
        role.map { SimpleGrantedAuthority("ROLE_$it") }
    )


}