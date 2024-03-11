package com.example.days.domain.dailycheck.repository

import com.example.days.domain.dailycheck.dto.response.DailyCheckResponse
import com.example.days.domain.dailycheck.model.DailyCheck
import com.example.days.domain.resolution.model.Resolution
import org.springframework.data.jpa.repository.JpaRepository

interface DailyCheckRepository: JpaRepository<DailyCheck, Long> {
    fun findByResolutionId(resolutionId: Resolution): List<DailyCheck>
}