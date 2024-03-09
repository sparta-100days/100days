package com.example.days.domain.report.dto.request

import jakarta.validation.constraints.NotBlank

data class UserReportRequest(
    @field:NotBlank(message = "신고할 유저의 아이디를 입력해주세요")
    val reportedUserNickname: String,

    @field:NotBlank(message = "신고 사유를 입력해주세요")
    val content: String

)
