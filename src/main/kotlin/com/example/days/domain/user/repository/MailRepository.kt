package com.example.days.domain.user.repository

import com.example.days.domain.user.model.Mail
import org.springframework.data.jpa.repository.JpaRepository

interface MailRepository: JpaRepository<Mail, Long> {
//    fun findByEmail(email: String): Mail
    fun findByCode(code: String): Mail?
}