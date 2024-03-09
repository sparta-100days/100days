package com.example.days.domain.report.repository

import com.example.days.domain.report.model.UserReport
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface AReportRepository {

    fun findByPageableAndNickname(pageable: Pageable, nickname: String?): Page<UserReport>
}