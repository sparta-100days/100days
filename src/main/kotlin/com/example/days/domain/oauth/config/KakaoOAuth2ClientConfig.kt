package com.example.days.domain.oauth.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod

@Configuration
@EnableConfigurationProperties(OAuth2ClientProperties::class)
class KakaoOAuth2ClientConfig {

    @Value("\${spring.security.oauth2.client.registration.kakao.client-id}")
    private lateinit var clientId: String

    @Value("\${spring.security.oauth2.client.registration.kakao.client-secret}")
    private lateinit var clientSecret: String

    @Value("\${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private lateinit var redirectUri: String

    @Value("\${spring.security.oauth2.client.registration.kakao.scope}")
    private lateinit var scope: Set<String>

    @Bean
    fun clientRegistrationRepository(): ClientRegistrationRepository {
        val clientRegistration: ClientRegistration = ClientRegistration.withRegistrationId("kakao")
            .clientId(clientId)
            .clientSecret(clientSecret)
            .redirectUri(redirectUri)
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .scope(*scope.toTypedArray())
            .authorizationUri("https://kauth.kakao.com/oauth/authorize")
            .tokenUri("https://kauth.kakao.com/oauth/token")
            .userInfoUri("https://kapi.kakao.com/v2/user/me")
            .userNameAttributeName("id")
            .build()

        return InMemoryClientRegistrationRepository(clientRegistration)
    }
}