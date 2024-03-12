package com.example.days.domain.resolution.service

import com.example.days.domain.resolution.dto.request.ResolutionRequest
import com.example.days.domain.resolution.dto.response.ResolutionResponse
import com.example.days.domain.resolution.dto.response.SimpleResolutionResponse
import com.example.days.global.common.SortOrder
import org.springframework.data.domain.Page


interface ResolutionService {
    fun createResolution(resolutionRequest: ResolutionRequest, userId: Long): ResolutionResponse
    fun getResolutionById(resolutionId: Long): ResolutionResponse
    fun getResolutionListPaginated(page: Int, sortOrder: SortOrder?): Page<ResolutionResponse>
    fun updateResolution(resolutionId: Long, userId: Long ,resolutionRequest: ResolutionRequest): ResolutionResponse
    fun deleteResolution(resolutionId: Long, userId: Long)
    fun getResolutionRanking(): List<SimpleResolutionResponse>
}