package com.example.days.domain.resolution.service

import com.example.days.domain.resolution.dto.request.ResolutionRequest
import com.example.days.domain.resolution.dto.response.ResolutionResponse
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

interface ResolutionService {
    fun createResolution(resolutionRequest: ResolutionRequest): ResolutionResponse
    fun getResolutionById(resolutionId: Long): ResolutionResponse
    fun getResolutionList(): List<ResolutionResponse>
    fun updateResolution(resolutionId: Long, resolutionRequest: ResolutionRequest): ResolutionResponse
    fun deleteResolution(resolutionId: Long)
}