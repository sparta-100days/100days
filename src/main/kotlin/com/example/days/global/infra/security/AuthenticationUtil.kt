package com.example.days.global.infra.security

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails

object AuthenticationUtil {
    private fun getAuthenticatedUser(): UserDetails =
        SecurityContextHolder.getContext().authentication.principal as UserDetails

    fun getAuthenticationUserId() = getAuthenticatedUser().username.toLong()
    fun getAuthenticationUserStatus() = getAuthenticatedUser().authorities.toString()
    fun getAuthenticationUserRole() = getAuthenticatedUser().authorities.toString()
}