package com.example.days.domain.user.dto.response

import com.example.days.domain.user.model.User

data class LoginResponse(
    val nickName: String,
    val message: String
){
    companion object {
        fun from(user: User): LoginResponse {
            return LoginResponse(
                nickName = user.nickName,
                message = "로그인이 완료되었습니다."
            )
        }
    }
}
