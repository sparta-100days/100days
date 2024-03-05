package com.example.days.domain.dailycheck.service

import com.example.days.domain.dailycheck.dto.request.DailyCheckRequest
import com.example.days.domain.dailycheck.dto.response.DailyCheckResponse

interface DailyCheckService {
    fun createDailyCheck(resolutionId: Long, dailyCheckRequest: DailyCheckRequest): DailyCheckResponse
    fun getDailyCheckByList(resolutionId: Long): List<DailyCheckResponse>
    fun updateDailyCheck(resolutionId: Long, dailyCheckId: Long, dailyCheckRequest: DailyCheckRequest): DailyCheckResponse
    fun deleteDailyCheck(dailyCheckId: Long)
}