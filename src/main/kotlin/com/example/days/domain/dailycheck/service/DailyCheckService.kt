package com.example.days.domain.dailycheck.service

import com.example.days.domain.dailycheck.dto.request.DailyCheckRequest
import com.example.days.domain.dailycheck.dto.response.DailyCheckResponse

interface DailyCheckService {
    fun createDailyCheck(resolutionId: Long, userId: Long, dailyCheckRequest: DailyCheckRequest): DailyCheckResponse
    fun getDailyCheckByList(resolutionId: Long, userId: Long): List<DailyCheckResponse>
    fun updateDailyCheck(resolutionId: Long, userId: Long, dailyCheckId: Long, dailyCheckRequest: DailyCheckRequest): DailyCheckResponse
    fun deleteDailyCheck(resolutionId: Long, dailyCheckId: Long, userId: Long)
}