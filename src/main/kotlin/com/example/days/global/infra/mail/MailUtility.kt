package com.example.days.global.infra.mail

import com.example.days.global.infra.regex.RegexFunc
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.util.*

@Component
class MailUtility(
    private val passwordEncoder: PasswordEncoder,
    private val regexFunc: RegexFunc,
    @Value("\${mail.username}") private val username: String,
    @Autowired val javaMailSender: JavaMailSender
) {

    fun sendMailTemplate(email: String): String {
        val random = UUID.randomUUID().toString().substring(0, 8)

        val message = javaMailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)

        helper.setTo(email)
        helper.setSubject("회원가입을 위한 이메일 인증번호입니다.")
        helper.setText("이메일 인증 번호는" + random + "입니다.")
        helper.setFrom(username)

        javaMailSender.send(message)

        return random
    }

    fun randomPassword(): String {
        return UUID.randomUUID().toString().substring(0, 7) + "00" + "!"
    }

    fun passwordChangeEMail(email: String): String {
        val randomChange = randomPassword()
        val pass = passwordEncoder.encode(regexFunc.regexPassword(randomChange))

        val message = javaMailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)

        helper.setTo(email)
        helper.setSubject("임시 비밀번호를 발급해드립니다.")
        helper.setText("임시 비밀번호는" + randomChange + "입니다. \n " +
                "로그인 하신 뒤, 반드시 비밀번호를 변경해주세요.")
        helper.setFrom(username)

        javaMailSender.send(message)

        return pass
    }
}