package com.example.days.domain.user.dto.response

import com.example.days.domain.user.model.User

data class EmailResponse(
    val email: String
){
    companion object {
        fun from(user: User): EmailResponse {
            return EmailResponse(
                email = user.email
            )
        }
    }
}