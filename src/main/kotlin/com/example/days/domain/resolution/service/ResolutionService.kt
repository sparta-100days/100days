package com.example.days.domain.resolution.service

import com.example.days.domain.resolution.dto.request.ResolutionRequest
import com.example.days.domain.resolution.dto.response.ResolutionResponse
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

interface ResolutionService {
    fun createResolution(resolutionRequest: ResolutionRequest, userId: Long): ResolutionResponse
    fun getResolutionById(resolutionId: Long): ResolutionResponse
    fun getResolutionList(): List<ResolutionResponse>
    fun updateResolution(resolutionId: Long, userId: Long ,resolutionRequest: ResolutionRequest): ResolutionResponse
    fun deleteResolution(resolutionId: Long, userId: Long)
}