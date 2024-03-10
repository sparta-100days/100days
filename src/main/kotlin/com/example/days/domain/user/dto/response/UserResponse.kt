package com.example.days.domain.user.dto.response

import com.example.days.domain.user.model.User
import com.example.days.domain.user.model.Status
import java.time.LocalDate
import java.time.LocalDateTime

data class UserResponse (
    val id: Long,
    val email: String,
    val nickname: String,
    val birth: LocalDate,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val isDelete: Boolean,
    val status: Status,
){

    companion object {
        fun from(user: User): UserResponse {
            return UserResponse(
                id = user.id!!,
                email = user.email,
                nickname = user.nickname,
                birth = user.birth,
                isDelete = user.isDelete,
                createdAt = user.createdAt,
                updatedAt = user.createdAt,
                status = user.status
            )
        }
    }
}