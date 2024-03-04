package com.example.days.global.common.exception

data class EmailExistException(val email: String) : RuntimeException(
    "이미 존재하는 회사 이메일입니다."
)

data class NicknameExistException(val nickname: String) : RuntimeException(
    "이미 존재하는 회사 닉네임입니다."
)
