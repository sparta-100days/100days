package com.example.days.domain.report.dto.request

import com.example.days.domain.report.model.ReportStatus
import jakarta.validation.constraints.NotBlank

data class CommentReportRequest(
    @field: NotBlank(message = "신고할 댓글을 입력해주세요")
    val reportedComment: String,

    @field:NotBlank(message = "신고 상태를 입력해주세요")
    val reportStatus: ReportStatus,

    @field:NotBlank(message = "신고 사유를 입력해주세요")
    val content: String
)
