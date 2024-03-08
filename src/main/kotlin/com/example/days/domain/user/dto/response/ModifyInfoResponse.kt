package com.example.days.domain.user.dto.response

import com.example.days.domain.user.model.User
import java.time.LocalDate

data class ModifyInfoResponse(
    val email: String,
    val nickname: String,
    val birth: LocalDate
){
    companion object {
        fun from(user: User): ModifyInfoResponse {
            return ModifyInfoResponse(
                email = user.email,
                nickname = user.nickname,
                birth = user.birth
            )
        }
    }
}
