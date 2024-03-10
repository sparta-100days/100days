package com.example.days.domain.dailycheck.service

import com.example.days.domain.dailycheck.dto.request.DailyCheckRequest
import com.example.days.domain.dailycheck.dto.response.DailyCheckResponse
import com.example.days.domain.dailycheck.repository.DailyCheckRepository
import com.example.days.domain.resolution.repository.ResolutionRepository
import com.example.days.global.infra.security.AuthenticationUtil.getAuthenticationUserId
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DailyCheckServiceImpl(
    private val dailyCheckRepository: DailyCheckRepository,
    private val resolutionRepository: ResolutionRepository
):DailyCheckService {
    @Transactional
    override fun createDailyCheck(resolutionId: Long, userId: Long, request: DailyCheckRequest): DailyCheckResponse {
        val resolution = resolutionRepository.findByIdOrNull(resolutionId) ?: TODO("예외처리")

        if(userId == resolution.author.id){
            dailyCheckRepository.save(DailyCheckRequest.of(request, resolution))
        }
        return resolutionRepository.findByIdOrNull(resolutionId)
            ?.let{ dailyCheckRepository.save(DailyCheckRequest.of(request, it)) }
            ?.let { DailyCheckResponse.from(it) }
            ?: TODO("예외처리")
    }

    override fun getDailyCheckByList(resolutionId: Long, userId: Long): List<DailyCheckResponse> {
        val resolution = resolutionRepository.findByIdOrNull(resolutionId) ?: TODO("예외처리")
        if(userId == resolution.author.id){
            return dailyCheckRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
                .map { DailyCheckResponse.from(it) }
        }
        else TODO()
    }

    @Transactional
    override fun updateDailyCheck(resolutionId: Long, userId: Long, dailyCheckId: Long, request: DailyCheckRequest)
    : DailyCheckResponse {
        val resolution = resolutionRepository.findByIdOrNull(resolutionId) ?: TODO("예외처리")
        val dailyCheck = dailyCheckRepository.findByIdOrNull(dailyCheckId) ?: TODO()
        if(userId == resolution.author.id){
            dailyCheck.updateDailyCheck(request.memo, resolution)
            return DailyCheckResponse.from(dailyCheck)
        }
        else TODO()


    }

    @Transactional
    override fun deleteDailyCheck(resolutionId: Long, dailyCheckId: Long, userId: Long) {
        val resolution = resolutionRepository.findByIdOrNull(resolutionId) ?: TODO("예외처리")

        if(userId == resolution.author.id) {
            dailyCheckRepository.deleteById(dailyCheckId)
        }
        else TODO()
    }
}