package com.example.days.domain.user.service

import com.example.days.domain.user.dto.request.EmailRequest
import com.example.days.domain.user.repository.MailRepository
import com.example.days.global.infra.mail.MailUtility
import com.example.days.global.infra.redis.RedisUtil
import com.example.days.global.support.MailType
import org.springframework.stereotype.Service

@Service
class UserMailServiceImpl(
    val mailRepository: MailRepository,
    val mailUtility: MailUtility,
    val redisUtil: RedisUtil
) : UserMailService {

    override fun sendVerificationEmail(request: EmailRequest) {
        mailUtility.emailSender(request.email, MailType.VERIFYCODE)
    }

    override fun verifyCode(code: String): String {
        val codeGet = redisUtil.getData(key = code)
        if (codeGet == null) {
            throw IllegalArgumentException("인증번호가 일치하지 않습니다.")
        } else {
            mailRepository.findByCode(code)
        }

        return "인증번호가 일치합니다."
    }
}
