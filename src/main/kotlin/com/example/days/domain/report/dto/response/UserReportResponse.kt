package com.example.days.domain.report.dto.response

import com.example.days.domain.report.model.UserReport

data class UserReportResponse(
    val id: Long,
    val reportedUserNickname: String,
    val reportedUserStatus: String,
    val content: String,
    val reporterNickname: String
) {
    companion object {
        fun from(userReport: UserReport): UserReportResponse {
            return UserReportResponse(
                id = userReport.id!!,
                content = userReport.content,
                reportedUserStatus = userReport.reportStatus.toString(),
                reportedUserNickname = userReport.reportedUserId.nickname,
                reporterNickname = userReport.reporter.nickname
            )
        }
    }

}
