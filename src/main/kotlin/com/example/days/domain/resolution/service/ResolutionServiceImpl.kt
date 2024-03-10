package com.example.days.domain.resolution.service

import com.example.days.domain.category.repository.CategoryRepository
import com.example.days.domain.resolution.dto.request.ResolutionRequest
import com.example.days.domain.resolution.dto.response.ResolutionResponse
import com.example.days.domain.resolution.repository.ResolutionRepository
import com.example.days.domain.user.repository.UserRepository
import com.example.days.global.common.SortOrder
import org.springframework.data.domain.Page
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class ResolutionServiceImpl(
    private val resolutionRepository: ResolutionRepository,
    private val userRepository: UserRepository,
    private val categoryRepository: CategoryRepository
): ResolutionService {
    @Transactional
    override fun createResolution(request: ResolutionRequest, userId: Long): ResolutionResponse {
        val user = userRepository.findByIdOrNull(userId) ?: TODO()
        val category = categoryRepository.findByName(request.category) ?: TODO()
        val resolution = resolutionRepository.save(ResolutionRequest.of(request, category, user))
        return ResolutionResponse.from(resolution)
    }

    override fun getResolutionById(resolutionId: Long): ResolutionResponse {
        val resolution = getByIdOrNull(resolutionId)
        return ResolutionResponse.from(resolution)
    }

    override fun getResolutionListPaginated(page: Int, sortOrder: SortOrder?): Page<ResolutionResponse> {
        val resolutionList = resolutionRepository.findByPageable(page, sortOrder)
        return resolutionList.map { ResolutionResponse.from(it) }
    }

    @Transactional
    override fun updateResolution(resolutionId: Long, userId: Long, request: ResolutionRequest): ResolutionResponse {
        val category = categoryRepository.findByName(request.category) ?: TODO()
        val updatedResolution = getByIdOrNull(resolutionId)
        if(updatedResolution.author.id == userId){
            updatedResolution.updateResolution(request.title, request.description, category)
            return ResolutionResponse.from(updatedResolution)
        }
        else TODO("예외처리")

    }

    @Transactional
    override fun deleteResolution(resolutionId: Long, userId: Long) {
        val resolution = getByIdOrNull(resolutionId)
        if (resolution.author.id == userId){
            resolutionRepository.delete(resolution)
        }
        else TODO("예외처리")
    }

    fun getByIdOrNull(id: Long) = resolutionRepository.findByIdOrNull(id) ?: TODO("예외처리 구현예정")
}