package com.example.days.domain.admin.dto.request

data class SignUpAdminRequest(
    val nickname: String,
    val email: String,
    val password: String
)
