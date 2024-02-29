package com.example.days.domain.admin.dto.response

import com.example.days.domain.admin.model.Admin

data class AdminResponse(
    val id: Long
) {
    companion object {
        fun from(admin: Admin): AdminResponse {
            return AdminResponse(
                id = admin.id!!
            )
        }
    }
}
