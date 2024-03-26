package com.example.days.global.infra.security

import com.example.days.global.infra.security.jwt.JwtAuthenticationFilter
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .csrf { it.disable() }
            // 여기서부터 소셜 로그인 설정
//            .cors { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .oauth2Login {
//                it.userInfoEndpoint { u -> u.userService() }
                it.defaultSuccessUrl("/auth/login") // 로그인 성공시 주소
                it.failureUrl("/fail") // 로그인 실패시 주소
            }

//            .headers { it.frameOptions { option -> option.disable() } }
            .authorizeHttpRequests {
                // common
                it.requestMatchers(AntPathRequestMatcher("/api/mail/**")).permitAll() // mail
                it.requestMatchers(AntPathRequestMatcher("/api/users/search/**")).permitAll() // search
                it.requestMatchers(AntPathRequestMatcher("/api/messages/**")).permitAll() // message
                it.requestMatchers(AntPathRequestMatcher("/api/reports/**")).permitAll() // report

                 // user
                it.requestMatchers(AntPathRequestMatcher("/api/users/login")).permitAll() // login
                it.requestMatchers(AntPathRequestMatcher("/api/users/signup")).permitAll() // signUp

                 // admin
                it.requestMatchers(AntPathRequestMatcher("/api/admins/**")).permitAll() // login

                // swagger
                it.requestMatchers(AntPathRequestMatcher("/swagger-ui/**")).permitAll()
                it.requestMatchers(AntPathRequestMatcher("/v3/api-docs/**")).permitAll()

                it.requestMatchers(PathRequest.toH2Console()).permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling {
//                it.authenticationEntryPoint(authenticationEntryPoint)
//                it.accessDeniedHandler(accessDeniedHandler)
            }
            .headers { it.frameOptions { it1 -> it1.disable() } }
            .build()
    }
}