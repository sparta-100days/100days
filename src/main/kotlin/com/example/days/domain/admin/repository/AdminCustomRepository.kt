package com.example.days.domain.admin.repository

import com.example.days.domain.user.model.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface AdminCustomRepository {
    fun findByPageableUser(pageable: Pageable): Page<User>
}