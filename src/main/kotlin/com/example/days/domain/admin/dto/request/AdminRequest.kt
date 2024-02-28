package com.example.days.domain.admin.dto.request

data class AdminRequest(
    val nickname: String,
    val email: String,
    val password: String
)