package com.example.days.domain.user.repository

import com.example.days.domain.user.dto.request.UserRequest
import com.example.days.domain.user.dto.response.SignUpResponse
import com.example.days.domain.user.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {
    fun existsByEmail(email: String): Boolean
    fun findUserByEmail(email: String): User?
}