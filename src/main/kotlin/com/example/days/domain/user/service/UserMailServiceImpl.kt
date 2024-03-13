package com.example.days.domain.user.service

import com.example.days.domain.user.dto.request.EmailRequest
import com.example.days.domain.user.model.Mail
import com.example.days.domain.user.repository.MailRepository
import com.example.days.global.infra.mail.MailUtility
import org.springframework.stereotype.Service

@Service
class UserMailServiceImpl(
    val mailRepository: MailRepository,
    val mailUtility: MailUtility
) : UserMailService {

    override fun sendVerificationEmail(request: EmailRequest) {
        val mail = mailUtility.emailSender(request.email, "1")

        mailRepository.save(
            Mail(
                email = request.email,
                code = mail
            )
        )
    }

    override fun verifyCode(code: String) {
        val codeCheck = mailRepository.findByCode(code) ?: throw IllegalArgumentException("인증번호가 일치하지 않습니다.")
        val userCheck = mailRepository.findByEmail(codeCheck.email)

        mailRepository.save(userCheck)
    }
}