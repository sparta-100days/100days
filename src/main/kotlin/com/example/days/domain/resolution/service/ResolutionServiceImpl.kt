package com.example.days.domain.resolution.service

import com.example.days.domain.resolution.dto.request.ResolutionRequest
import com.example.days.domain.resolution.dto.response.ResolutionResponse
import com.example.days.domain.resolution.model.Resolution
import com.example.days.domain.resolution.repository.ResolutionRepository
import com.example.days.domain.user.repository.UserRepository
import com.example.days.global.infra.security.AuthenticationUtil.getAuthenticationUserId
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ResolutionServiceImpl(
    private val resolutionRepository: ResolutionRepository,
    private val userRepository: UserRepository
): ResolutionService {
    @Transactional
    override fun createResolution(request: ResolutionRequest): ResolutionResponse {
        val user = getAuthenticationUserId()
            .let { userRepository.findByIdOrNull(it) }
            ?: TODO("예외처리")
        val resolution = resolutionRepository.save(ResolutionRequest.of(request, user))
        return ResolutionResponse.from(resolution)
    }

    override fun getResolutionById(resolutionId: Long): ResolutionResponse {
        val resolution = getByIdOrNull(resolutionId)
        return ResolutionResponse.from(resolution)
    }

    override fun getResolutionList(): List<ResolutionResponse> {
        return resolutionRepository.findAll().map{ResolutionResponse.from(it)}
    }

    @Transactional
    override fun updateResolution(resolutionId: Long, request: ResolutionRequest): ResolutionResponse {
        val user = getAuthenticationUserId()
        val updatedResolution = getByIdOrNull(resolutionId)
        if(updatedResolution.author.id == user){
            updatedResolution.updateResolution(request.title, request.description, request.category)
            return ResolutionResponse.from(updatedResolution)
        }
        else TODO("예외처리")

    }

    @Transactional
    override fun deleteResolution(resolutionId: Long) {
        val user = getAuthenticationUserId()
        val resolution = getByIdOrNull(resolutionId)
        if (resolution.author.id == user){
            resolutionRepository.delete(resolution)
        }
        else TODO("예외처리")
    }

    fun getByIdOrNull(id: Long) = resolutionRepository.findByIdOrNull(id) ?: TODO("예외처리 구현예정")
}