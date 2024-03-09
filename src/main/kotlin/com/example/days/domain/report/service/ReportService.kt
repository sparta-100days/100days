package com.example.days.domain.report.service

import com.example.days.domain.report.dto.request.UserReportRequest
import com.example.days.domain.report.dto.response.UserReportResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ReportService {

    fun reportUser(req: UserReportRequest, userId: Long): UserReportResponse

    fun getReportUser(pageable: Pageable, nickname: String): Page<UserReportResponse>
}