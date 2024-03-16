package com.example.days.domain.report.service

import com.example.days.domain.report.dto.request.UserReportRequest
import com.example.days.domain.report.dto.response.UserReportResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ReportService {

    // 유저 신고
    fun reportUser(req: UserReportRequest, userId: Long): UserReportResponse

}