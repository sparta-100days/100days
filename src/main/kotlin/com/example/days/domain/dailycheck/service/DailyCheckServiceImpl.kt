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
    override fun createDailyCheck(resolutionId: Long, request: DailyCheckRequest): DailyCheckResponse {
        val userId = getAuthenticationUserId()
        val resolution = resolutionRepository.findByIdOrNull(resolutionId) ?: TODO("예외처리")

        if(userId == resolution.author.id){
            dailyCheckRepository.save(DailyCheckRequest.of(request, resolution))
        }
        return resolutionRepository.findByIdOrNull(resolutionId)
            ?.let{ dailyCheckRepository.save(DailyCheckRequest.of(request, it)) }
            ?.let { DailyCheckResponse.from(it) }
            ?: TODO("예외처리")
    }

    override fun getDailyCheckByList(resolutionId: Long): List<DailyCheckResponse> {
        return dailyCheckRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
            .map { DailyCheckResponse.from(it) }
    }

    @Transactional
    override fun updateDailyCheck(resolutionId: Long, dailyCheckId: Long, request: DailyCheckRequest)
    : DailyCheckResponse {
        val resolution = resolutionRepository.findByIdOrNull(resolutionId) ?: TODO()
        val dailyCheck = dailyCheckRepository.findByIdOrNull(dailyCheckId) ?: TODO()
        dailyCheck.updateDailyCheck(request.memo, resolution)
        return DailyCheckResponse.from(dailyCheck)
    }

    @Transactional
    override fun deleteDailyCheck(dailyCheckId: Long) {
        dailyCheckRepository.deleteById(dailyCheckId)
    }
}