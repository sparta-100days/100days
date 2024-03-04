package com.example.days.domain.dailycheck.service

import com.example.days.domain.dailycheck.dto.request.DailyCheckRequest
import com.example.days.domain.dailycheck.dto.response.DailyCheckResponse
import com.example.days.domain.dailycheck.repository.DailyCheckRepository
import com.example.days.domain.resolution.repository.ResolutionRepository
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
    override fun createDailyCheck(resolutionId: Long, Request: DailyCheckRequest): DailyCheckResponse {
        return resolutionRepository.findByIdOrNull(resolutionId)
            ?.let{ dailyCheckRepository.save(DailyCheckRequest.of(Request, it)) }
            ?.let { DailyCheckResponse.from(it) }
            ?: TODO("예외처리")
    }

    override fun getDailyCheckByList(resolutionId: Long): List<DailyCheckResponse> {
        return dailyCheckRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
            .map { DailyCheckResponse.from(it) }
    }

    @Transactional
    override fun updateDailyCheck(resolutionId: Long, dailyCheckId: Long, dailyCheckRequest: DailyCheckRequest)
    : DailyCheckResponse {
        // ^오^: 밖으로 뺜다면 이쪽으로 빠질 예정
        return dailyCheckRepository.findByIdOrNull(dailyCheckId)
            ?.let {
                // ^오^: 아래 updatedResolution 이 코드를 밖으로 뺄 지 let 안에 둘지 고민입니다.
                val updatedResolution = resolutionRepository.findByIdOrNull(resolutionId) ?: TODO("예외처리")
                it.updateDailyCheck(dailyCheckRequest, updatedResolution) }
            ?.let { DailyCheckResponse.from(it) }
            ?: TODO("예외처리")
    }

    override fun deleteDailyCheck(dailyCheckId: Long) {
        dailyCheckRepository.deleteById(dailyCheckId)
    }
}