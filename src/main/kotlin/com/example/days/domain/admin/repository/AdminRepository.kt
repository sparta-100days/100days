package com.example.days.domain.admin.repository

import com.example.days.domain.admin.model.Admin
import org.springframework.data.jpa.repository.JpaRepository

interface AdminRepository : JpaRepository<Admin, Long> {
}