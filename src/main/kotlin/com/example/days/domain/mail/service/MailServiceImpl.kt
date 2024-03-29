package com.example.days.domain.mail.service

import com.example.days.domain.mail.dto.request.EmailRequest
import com.example.days.domain.mail.repository.MailRepository
import com.example.days.global.common.exception.user.AuthCodeMismatchException
import com.example.days.global.infra.mail.MailUtility
import com.example.days.global.infra.redis.RedisUtil
import com.example.days.global.support.MailType
import org.springframework.stereotype.Service

@Service
class MailServiceImpl(
    val mailRepository: MailRepository,
    val mailUtility: MailUtility,
    val redisUtil: RedisUtil
) : MailService {

    override fun sendVerificationEmail(request: EmailRequest) {
        mailUtility.emailSender(request.email, MailType.VERIFYCODE)
    }

    override fun verifyCode(code: String): String {
        val codeGet = redisUtil.getData(key = code)
        if (codeGet == null) {
            throw AuthCodeMismatchException()
        } else {
            mailRepository.findByCode(code)
        }
        return "인증번호가 일치합니다."
    }
}
