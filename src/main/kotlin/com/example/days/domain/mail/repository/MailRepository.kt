package com.example.days.domain.mail.repository

import com.example.days.domain.mail.model.Mail
import org.springframework.data.jpa.repository.JpaRepository

interface MailRepository: JpaRepository<Mail, Long> {
    fun findByCode(code: String): Mail?
}