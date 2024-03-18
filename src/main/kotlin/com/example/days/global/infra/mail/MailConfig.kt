package com.example.days.global.infra.mail

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSenderImpl
import java.util.*

@Configuration
class MailConfig(
    @Value("\${mail_host}") private val host: String,
    @Value("\${mail.port}") private val port: Int,
    @Value("\${mail_username}") private val username: String,
    @Value("\${mail_password}") private val password: String,
    @Value("\${mail.properties.mail.smtp.auth}") private var auth: Boolean,
    @Value("\${mail.properties.mail.smtp.starttls.enable}") private var enable: Boolean,
    @Value("\${mail.properties.mail.smtp.starttls.required}") private var required: Boolean
) {

    @Bean
    fun mailSender(): JavaMailSenderImpl {
        val javaMailSender = JavaMailSenderImpl()

        javaMailSender.host = host
        javaMailSender.port = port
        javaMailSender.username = username
        javaMailSender.password = password


        val props = Properties()
        props["mail_smtp_auth"] = auth.toString()
        props["mail_smtp_starttls_enable"] = enable.toString()
        props["mail_smtp_starttls_required"] = required.toString()

        javaMailSender.javaMailProperties = props

        return javaMailSender
    }
}