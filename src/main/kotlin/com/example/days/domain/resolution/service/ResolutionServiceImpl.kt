package com.example.days.domain.resolution.service

import com.example.days.domain.resolution.dto.request.ResolutionRequest
import com.example.days.domain.resolution.dto.response.ResolutionResponse
import com.example.days.domain.resolution.model.Resolution
import com.example.days.domain.resolution.repository.ResolutionRepository
import org.springframework.data.repository.findByIdOrNull

class ResolutionServiceImpl(
    private val resolutionRepository: ResolutionRepository
): ResolutionService {
    override fun createResolution(request: ResolutionRequest): ResolutionResponse {
        // TODO : 사용자 식별 가능한 로직 추가 예졍
        val resolution = resolutionRepository.save(Resolution.of(request, user))
        return resolution.from()
    }

    override fun getResolutionById(resolutionId: Long): ResolutionResponse {
        val resolution = getByIdOrNull(resolutionId)
        return resolution.from()
    }

    override fun getResolutionList(): List<ResolutionResponse> {
        return resolutionRepository.findAll().map{it.from()}
    }

    override fun updateResolution(resolutionId: Long, request: ResolutionRequest): ResolutionResponse {
        // TODO : 목표 작성자만 수정 가능하도록 제한
        val updatedResolution = getByIdOrNull(resolutionId)
        updatedResolution.updateResolution(request)
        return updatedResolution.from()
    }

    override fun deleteResolution(resolutionId: Long) {
        // TODO : 목표 작성자만 삭제 가능하도록 제한
        val resolution = getByIdOrNull(resolutionId)
        resolutionRepository.delete(resolution)
    }

    fun getByIdOrNull(id: Long) = resolutionRepository.findByIdOrNull(id) ?: TODO("예외처리 구현예정")
}