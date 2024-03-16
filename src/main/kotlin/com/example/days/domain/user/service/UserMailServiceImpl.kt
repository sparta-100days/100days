package com.example.days.domain.user.service

import com.example.days.domain.user.dto.request.EmailRequest
import com.example.days.domain.user.model.Mail
import com.example.days.domain.user.repository.MailRepository
import com.example.days.global.common.exception.user.AuthCodeMismatchException
import com.example.days.global.infra.mail.MailUtility
import org.springframework.stereotype.Service

@Service
class UserMailServiceImpl(
    val mailRepository: MailRepository,
    val mailUtility: MailUtility
) : UserMailService {

    override fun sendEmail(request: EmailRequest) {
        val mail = mailUtility.sendMailTemplate(request.email)

        mailRepository.save(
            Mail(
                email = request.email,
                code = mail
            )
        )
    }

    override fun verifyCode(code: String) {
        val codeCheck = mailRepository.findByCode(code) ?: throw AuthCodeMismatchException()
        val userCheck = mailRepository.findByEmail(codeCheck.email)

        mailRepository.save(userCheck)
    }
}