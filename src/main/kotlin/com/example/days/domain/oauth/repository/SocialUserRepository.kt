package com.example.days.domain.oauth.repository

import com.example.days.domain.oauth.model.OAuth2Provider
import com.example.days.domain.oauth.model.SocialUser
import com.example.days.domain.user.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SocialUserRepository : CrudRepository<SocialUser, Long> {
    fun existsByProviderAndProviderId(provider: OAuth2Provider, toString: String): Boolean
    fun findByProviderAndProviderId(provider: OAuth2Provider, toString: String): SocialUser
//    fun findByEmail(email: String): SocialUser
}