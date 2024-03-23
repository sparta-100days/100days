package com.example.days.domain.oauth.client.oauth2.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod

@Configuration
class OAuth2Config {

    @Bean
    fun clientRegistrationRepository(): ClientRegistrationRepository {
/*
        val googleClientRegistration = ClientRegistration
            .withRegistrationId("google")
            .clientId("your-client-id")
            .clientSecret("your-client-secret")
            .redirectUri("{baseUrl}/login/oauth2/code/google")
            .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
            .tokenUri("https://www.googleapis.com/oauth2/v4/token")
            .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
            .userNameAttributeName("id")
            .clientName("Google")
            .build()
*/
        val kakaoClientRegistration = ClientRegistration
            .withRegistrationId("kakao")
            .clientId("your-client-id")
            .clientSecret("your-client-secret")
            .redirectUri("http://localhost:8080/oauth2/callback/kakao")
            .authorizationUri("https://kauth.kakao.com/oauth/authorize")
            .tokenUri("https://kauth.kakao.com/oauth/token")
            .userInfoUri("https://kapi.kakao.com/v2/user/me")
            .userNameAttributeName("id")
            .clientName("Kakao")
            .scope("profile_nickname,account_email")
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
            .build()

        return InMemoryClientRegistrationRepository(kakaoClientRegistration)
    }
}