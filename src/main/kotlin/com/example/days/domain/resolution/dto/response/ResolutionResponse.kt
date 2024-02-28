package com.example.days.domain.resolution.dto.response

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class ResolutionResponse(
    val id: Long?,
    val title: String,
    val description: String,
    val completeStatus: Boolean,
    val dailyStatus: Boolean,
    val category: String,
    val likeCount: Long,

    // ^오^: 시간 형식으로 값을 바꿔주는 어노테이션
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val updatedAt: LocalDateTime
)
