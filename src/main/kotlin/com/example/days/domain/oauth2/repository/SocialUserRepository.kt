package com.example.days.domain.oauth2.repository

import com.example.days.domain.user.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SocialUserRepository: CrudRepository<User, Long> {
}