package com.example.days.global.infra.security.jwt

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
class SecurityConfig() {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .csrf { it.disable() }
            .authorizeHttpRequests {

                it.requestMatchers(AntPathRequestMatcher("/api/users")).permitAll()
                it.requestMatchers(AntPathRequestMatcher("/api/users/signup")).permitAll()
                it.requestMatchers(AntPathRequestMatcher("/api/users/login")).permitAll()
                it.requestMatchers(AntPathRequestMatcher("/swagger-ui/**")).permitAll()
                it.requestMatchers(AntPathRequestMatcher("/v3/api-docs/**")).permitAll()
                    .anyRequest().authenticated()
            }
//            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling {
//                it.authenticationEntryPoint(authenticationEntryPoint)
//                it.accessDeniedHandler(accessDeniedHandler)
            }
            .build()
    }
}