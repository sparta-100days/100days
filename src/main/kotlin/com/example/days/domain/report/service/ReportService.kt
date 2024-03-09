package com.example.days.domain.report.service

import com.example.days.domain.report.dto.request.UserReportRequest
import com.example.days.domain.report.dto.response.UserReportResponse

interface ReportService {

    fun reportUser(req: UserReportRequest, userId: Long): UserReportResponse

    fun getReportUser(): List<UserReportResponse>
}