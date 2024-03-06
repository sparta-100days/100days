package com.example.days.global.infra.mail

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component
import java.util.*

@Component
class MailUtility(
    @Value("\${mail.username}") private val username: String,
    @Autowired val javaMailSender: JavaMailSender
) {

    fun getRandomString(): String {
        val random = UUID.randomUUID().toString()
        return random.substring(0, 8)
    }

    fun sendMailTemplate(email: String): String {
        val random = getRandomString()

        val message = javaMailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)

        helper.setTo(email)
        helper.setSubject("회원가입을 위한 이메일 인증번호입니다.")
        helper.setText("이메일 인증 번호는" + random + "입니다.")
        helper.setFrom(username)

        javaMailSender.send(message)

        return random
    }
}