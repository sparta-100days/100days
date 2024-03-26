package com.example.days.domain.report.dto.response

import com.example.days.domain.report.model.PostReport
import com.example.days.domain.report.model.UserReport

data class PostReportResponse (
    val id: Long,
    val reportedPost: String,
    val content: String,
    val reporterNickname: String
) {
    companion object {
        fun from(postReport: PostReport): PostReportResponse {
            return PostReportResponse(
                id = postReport.id!!,
                content = postReport.content,
                reportedPost = postReport.reportedPost.title,
                reporterNickname = postReport.reporter.nickname
            )
        }
    }
}
