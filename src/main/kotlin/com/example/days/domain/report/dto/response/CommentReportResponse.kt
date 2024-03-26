package com.example.days.domain.report.dto.response

import com.example.days.domain.report.model.CommentReport
import com.example.days.domain.report.model.PostReport

data class CommentReportResponse(
    val id: Long,
    val reportedComment: String,
    val content: String,
    val reporterNickname: String
) {
    companion object {
        fun from(commentReport: CommentReport): CommentReportResponse {
            return CommentReportResponse(
                id = commentReport.id!!,
                content = commentReport.content,
                reportedComment = commentReport.reportedComment.comment,
                reporterNickname = commentReport.reporter.nickname
            )
        }
    }
}
