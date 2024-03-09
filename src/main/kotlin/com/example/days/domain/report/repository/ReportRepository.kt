package com.example.days.domain.report.repository

import com.example.days.domain.report.model.UserReport
import org.springframework.data.jpa.repository.JpaRepository

interface ReportRepository: JpaRepository<UserReport, Long>, AReportRepository {
}