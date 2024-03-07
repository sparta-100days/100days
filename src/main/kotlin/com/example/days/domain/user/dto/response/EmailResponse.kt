package com.example.days.domain.user.dto.response

import com.example.days.domain.user.model.User
import java.time.format.DateTimeFormatter

data class EmailResponse(
    val email: String,
    val createdAt: String,
){
    companion object {
        fun from(user: User): EmailResponse {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val formattedDate = user.createdAt.format(formatter)
            val maskedEmail = maskEmail(user.email)
            return EmailResponse(
                email = maskedEmail,
                createdAt = formattedDate
            )
        }

        private fun maskEmail(email: String): String {
            val atIndex = email.indexOf('@')
            return if (atIndex != -1) {
                val masked = email.substring(0, 3) + "*".repeat(atIndex - 3)
                val mailDomain = email.substring(atIndex)
                "$masked$mailDomain"
            } else {
                email
            }
        }
    }
}