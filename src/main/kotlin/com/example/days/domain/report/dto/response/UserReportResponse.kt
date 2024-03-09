package com.example.days.domain.report.dto.response

import com.example.days.domain.report.model.UserReport
import com.example.days.domain.user.model.Status

data class UserReportResponse(
    val id: Long,
    val reportedUserNickname: String,
    val reportedUserStatus: Status,
    val content: String
) {
    companion object {
        fun from(userReport: UserReport): UserReportResponse {
            return UserReportResponse(
                id = userReport.id!!,
                content = userReport.content,
                reportedUserStatus = userReport.reportedUserId.status,
                reportedUserNickname = userReport.reportedUserId.nickname
            )
        }
    }

}
