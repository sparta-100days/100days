package com.example.days.domain.admin.dto.request

// ㅇㅅㅇ 이는 아직 사용하지 않지만 회원가입 외에도 추가할 수도 있으므로 남겨둠
data class AdminRequest(
    val nickname: String,
    val email: String,
    val password: String
)