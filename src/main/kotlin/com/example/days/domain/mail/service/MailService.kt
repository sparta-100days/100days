package com.example.days.domain.mail.service

import com.example.days.domain.mail.dto.request.EmailRequest

interface MailService {
    fun sendVerificationEmail(request: EmailRequest)
    fun verifyCode(code: String): String
}