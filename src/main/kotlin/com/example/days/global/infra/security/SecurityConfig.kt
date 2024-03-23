package com.example.days.global.infra.security

import com.example.days.domain.oauth.service.OAuth2LoginSuccessHandler
import com.example.days.domain.oauth.service.SocialUserService
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
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val authenticationEntryPoint: CustomAuthenticationEntryPoint,
    private val accessDeniedHandler: CustomAccessDeniedHandler,
    private val oAuth2UserService: SocialUserService,
    private val oAuth2LoginSuccessHandler: OAuth2LoginSuccessHandler
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .csrf { it.disable() }
            .cors { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .headers { it.frameOptions { option -> option.disable() } }
            .authorizeHttpRequests {
                it.requestMatchers(AntPathRequestMatcher("/api/users")).permitAll()
                it.requestMatchers(AntPathRequestMatcher("/api/admins/**")).permitAll()
                it.requestMatchers(AntPathRequestMatcher("/api/users/signup")).permitAll()
                it.requestMatchers(AntPathRequestMatcher("/api/messages/**")).permitAll()
                it.requestMatchers(AntPathRequestMatcher("/api/reports/**")).permitAll()
                it.requestMatchers(AntPathRequestMatcher("/api/users/login")).permitAll()
                it.requestMatchers(AntPathRequestMatcher("/api/users/searchEmail")).permitAll()
                it.requestMatchers(AntPathRequestMatcher("/api/users/searchPass")).permitAll()
                it.requestMatchers(AntPathRequestMatcher("/api/mail")).permitAll()
                it.requestMatchers(AntPathRequestMatcher("/api/mail/sendmail")).permitAll()
                it.requestMatchers(AntPathRequestMatcher("/api/mail/verifycode")).permitAll()
                it.requestMatchers(AntPathRequestMatcher("/swagger-ui/**")).permitAll()
                it.requestMatchers(AntPathRequestMatcher("/v3/api-docs/**")).permitAll()
                it.requestMatchers(AntPathRequestMatcher("/oauth2/**")).permitAll()
                it.requestMatchers(AntPathRequestMatcher("/**")).permitAll()
                it.requestMatchers(PathRequest.toH2Console()).permitAll()
                    .anyRequest().authenticated()
            }
            .oauth2Login { oauthConfig ->
                oauthConfig.authorizationEndpoint {
                    it.baseUri("/api/v1/oauth2/login")
                }.redirectionEndpoint {
                    it.baseUri("/api/v1/oauth2/callback/*")
                }.userInfoEndpoint {
                    it.userService(oAuth2UserService)
                }.successHandler(oAuth2LoginSuccessHandler)
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