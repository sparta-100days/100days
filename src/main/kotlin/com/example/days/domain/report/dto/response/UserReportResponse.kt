package com.example.days.domain.report.dto.response

import com.example.days.domain.report.model.UserReport

data class UserReportResponse(
    val id: Long,
    val reportedUserNickname: String,
    val content: String,
    val reporterNickname: String
) {
    // 신고 당한 사람 닉네임, 신고내용, 신고한 사람 닉네임 (신고 당한 총 수를 넣는 게 맞을까?)
    companion object {
        fun from(userReport: UserReport): UserReportResponse {
            return UserReportResponse(
                id = userReport.id!!,
                content = userReport.content,
                reportedUserNickname = userReport.reportedUserId.nickname,
                reporterNickname = userReport.reporter.nickname
            )
        }
    }

}
