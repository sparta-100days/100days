package com.example.days.global.infra.security.jwt

import com.example.days.global.infra.security.UserPrincipal
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtPlugin: JwtPlugin
) : OncePerRequestFilter() {

    companion object {
        private val BEARER_PATTERN = Regex("^Bearer (.+?)$")
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val jwt = request.getBearerToken()

        if (jwt != null) {
            jwtPlugin.validateToken(jwt)
                .onSuccess {
                    // accessToken
                    val id = it.payload.subject.toLong()
                    val email = it.payload.get("email", String::class.java)
                    val role = it.payload.get("role", String::class.java)

                    val principal = UserPrincipal(
                        id = id,
                        email = email,
                        role = setOf(role)
                    )
                    val authentication = JwtAuthenticationToken(
                        principal = principal,
                        details = WebAuthenticationDetailsSource().buildDetails(request)
                    )

                    SecurityContextHolder.getContext().authentication = authentication

                }.onFailure {
                    val refreshToken = request.getHeader(HttpHeaders.AUTHORIZATION)?.let { headerValue ->
                        BEARER_PATTERN.find(headerValue)?.groupValues?.get(1)
                    }

                    refreshToken?.let { e ->
                        try {
                            jwtPlugin.vaildateRefreshToken(refreshToken, jwt)

                            val newAccessToken = jwtPlugin.recreateAccessToken(jwt)
                            response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer $newAccessToken")

                        } catch (e : IllegalArgumentException) {
                            throw IllegalArgumentException("refresh token이 만료되었습니다.")
                        }
                    }
                }
        }
        filterChain.doFilter(request, response)
    }

    private fun HttpServletRequest.getBearerToken(): String? {
        val headerValue = this.getHeader(HttpHeaders.AUTHORIZATION) ?: return null
        return BEARER_PATTERN.find(headerValue)?.groupValues?.get(1)
    }
}