package com.example.days.global.infra.security

import com.example.days.global.infra.security.jwt.JwtAuthenticationFilter
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val authenticationEntryPoint: CustomAuthenticationEntryPoint,
    private val accessDeniedHandler: CustomAccessDeniedHandler
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .csrf { it.disable() }
            .authorizeHttpRequests {

                it.requestMatchers(AntPathRequestMatcher("/api/users")).permitAll()
                it.requestMatchers(AntPathRequestMatcher("/api/admins/**")).permitAll()
                it.requestMatchers(AntPathRequestMatcher("/api/users/signup")).permitAll()
                it.requestMatchers(AntPathRequestMatcher("/api/messages/**")).permitAll()
                it.requestMatchers(AntPathRequestMatcher("/api/reports/**")).permitAll()
                it.requestMatchers(AntPathRequestMatcher("/api/users/login")).permitAll()
                it.requestMatchers(AntPathRequestMatcher("/api/users/searchEmail")).permitAll()
                it.requestMatchers(AntPathRequestMatcher("/api/users/searchPass")).permitAll()
                it.requestMatchers(AntPathRequestMatcher("/admin/signup")).permitAll()
                it.requestMatchers(AntPathRequestMatcher("/api/mail")).permitAll()
                it.requestMatchers(AntPathRequestMatcher("/api/mail/sendmail")).permitAll()
                it.requestMatchers(AntPathRequestMatcher("/api/mail/verifycode")).permitAll()
                it.requestMatchers(AntPathRequestMatcher("/swagger-ui/**")).permitAll()
                it.requestMatchers(AntPathRequestMatcher("/v3/api-docs/**")).permitAll()
                it.requestMatchers(PathRequest.toH2Console()).permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling {
//                it.authenticationEntryPoint(authenticationEntryPoint)
                it.accessDeniedHandler(accessDeniedHandler)
            }
            .headers { it.frameOptions { it1 -> it1.disable() } }
            .build()
    }
}