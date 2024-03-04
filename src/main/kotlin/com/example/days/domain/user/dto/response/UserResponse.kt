package com.example.days.domain.user.dto.response

import com.example.days.domain.user.model.User
import com.example.days.domain.user.model.UserStatus
import java.time.LocalDate

data class UserResponse (
    val id: Long,
    val email: String,
    val nickName: String,
    val birth: LocalDate,
    val createdAt: LocalDate,
    val updatedAt: LocalDate,
    val isDelete: Boolean,
    val status: String,
){
    companion object {
        fun from(user: User): UserResponse {
            return UserResponse(
                id = user.id!!,
                email = user.email,
                nickName = user.nickName,
                birth = user.birth,
                isDelete = false,
                createdAt = LocalDate.now(),
                updatedAt = LocalDate.now(),
                status = UserStatus.ACTIVE.toString()
            )
        }
    }
}