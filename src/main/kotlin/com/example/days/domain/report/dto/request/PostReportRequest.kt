package com.example.days.domain.report.dto.request

import com.example.days.domain.report.model.ReportStatus
import jakarta.validation.constraints.NotBlank

data class PostReportRequest(
    @field: NotBlank(message = "신고할 포스트를 입력해주세요")
    val reportedPost: String,

    @field:NotBlank(message = "신고 상태를 입력해주세요")
    val reportStatus: ReportStatus,

    @field:NotBlank(message = "신고 사유를 입력해주세요")
    val content: String
)
