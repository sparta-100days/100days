package com.example.days.domain.dailycheck.dto.response

import com.example.days.domain.dailycheck.dto.request.DailyCheckRequest
import com.example.days.domain.dailycheck.model.DailyCheck
import com.example.days.domain.resolution.model.Resolution
import java.time.LocalDateTime

data class DailyCheckResponse(
    val id: Long?,
    val memo: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
){
    companion object{
        fun from(dailyCheck: DailyCheck) = DailyCheckResponse(
                id = dailyCheck.id,
                memo = dailyCheck.memo,
                createdAt = dailyCheck.createdAt,
                updatedAt = dailyCheck.updatedAt
            )
    }
}
