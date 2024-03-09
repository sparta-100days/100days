package com.example.days.domain.report.repository

import com.example.days.domain.report.model.UserReport
import com.example.days.domain.user.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface ReportRepository: JpaRepository<UserReport, Long>, AReportRepository {
//    fun countByReportedUserId(reportedUserId: User): Long
}