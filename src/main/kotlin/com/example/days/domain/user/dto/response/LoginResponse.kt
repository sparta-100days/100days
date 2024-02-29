package com.example.days.domain.user.dto.response

import com.example.days.domain.user.model.User

data class LoginResponse(
    val nickName: String,
    val message: String = "로그인이 완료되었습니다."
){
    companion object {
        fun from(user: User): LoginResponse {
            return LoginResponse(
                nickName = user.nickName,
                message = user.toString()
            )
        }
    }
}
