package com.example.days.domain.report.dto.request

import com.example.days.domain.report.model.UserReportStatus
import jakarta.validation.constraints.NotBlank

data class UserReportRequest(
    @field:NotBlank(message = "신고할 유저의 닉네임을 입력해주세요")
    val reportedUserNickname: String,

    @field:NotBlank(message = "신고 상태를 입력해주세요")
    val reportStatus: UserReportStatus,

    @field:NotBlank(message = "신고 사유를 입력해주세요")
    val content: String

)
