package com.example.days.domain.dailycheck.dto.request

import com.example.days.domain.dailycheck.model.DailyCheck
import com.example.days.domain.resolution.model.Resolution

data class DailyCheckRequest(
    val memo: String
) {
    companion object {
        fun of(request: DailyCheckRequest, resolution: Resolution): DailyCheck {
            return DailyCheck(
                memo = request.memo,
                resolutionId = resolution
            )
        }
    }
}

