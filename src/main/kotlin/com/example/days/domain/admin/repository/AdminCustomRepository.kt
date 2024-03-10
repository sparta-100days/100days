package com.example.days.domain.admin.repository

import com.example.days.domain.user.model.Status
import com.example.days.domain.user.model.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface AdminCustomRepository {
    fun findByPageableUserAndStatus(pageable: Pageable, status: Status?): Page<User>
}