package com.example.days.domain.oauth.repository

import com.example.days.domain.oauth.model.SocialLoginUser
import org.springframework.data.repository.CrudRepository

interface SocialUserLoginRepository: CrudRepository<SocialLoginUser, String> {
}