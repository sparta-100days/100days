package com.example.days.domain.user.dto.response

import com.example.days.domain.user.model.User

data class AccountSearchResponse (
    val accountId: String,
    val nickname: String
){
    companion object {
        fun from(user: User): AccountSearchResponse {
            val add = "@" + user.accountId
            val name = "@" + user.nickname
            return AccountSearchResponse(accountId = add, nickname = name)
        }
    }
}