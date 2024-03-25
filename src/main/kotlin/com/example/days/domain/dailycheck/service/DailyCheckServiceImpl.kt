package com.example.days.domain.dailycheck.service

import com.example.days.domain.dailycheck.dto.request.DailyCheckRequest
import com.example.days.domain.dailycheck.dto.response.DailyCheckResponse
import com.example.days.domain.dailycheck.repository.DailyCheckRepository
import com.example.days.domain.post.service.PostService
import com.example.days.domain.resolution.repository.ResolutionRepository
import com.example.days.global.common.exception.auth.PermissionDeniedException
import com.example.days.global.common.exception.common.CheckAlreadyCompletedException
import com.example.days.global.common.exception.common.ModelNotFoundException
import com.example.days.global.common.exception.common.ResolutionAlreadyCompletedException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DailyCheckServiceImpl(
    private val dailyCheckRepository: DailyCheckRepository,
    private val resolutionRepository: ResolutionRepository,
    private val postService: PostService // 추가해보려고 일단 넣어봤는데 필요없으심 삭제하셔도 됩니다!
) : DailyCheckService {
    @Transactional
    override fun createDailyCheck(resolutionId: Long, userId: Long, request: DailyCheckRequest): DailyCheckResponse {
        val resolution = getByIdOrNull(resolutionId)

        if (userId == resolution.author.id) {
            when {
                resolution.dailyStatus -> throw CheckAlreadyCompletedException()
                resolution.completeStatus -> throw ResolutionAlreadyCompletedException()
                else -> return resolutionRepository.findByIdOrNull(resolutionId)
                    ?.let {
                        it.updateProgress()
                        // ~ @ ~
                        // 여기 부분에 포스트 작성을 넣어주면 작동할것같습니다!
                        // PostType.CHECK 는 제목만 받고 PostType.POST는 전체 포스트 작성하게 해두었습니다.
                        dailyCheckRepository.save(DailyCheckRequest.of(request, it))
                    }
                    ?.let { DailyCheckResponse.from(it) }
                    ?: throw ModelNotFoundException("Resolution", resolutionId)
            }
        } else throw PermissionDeniedException()
    }

    override fun getDailyCheckByList(resolutionId: Long, userId: Long): List<DailyCheckResponse> {
        val resolution = getByIdOrNull(resolutionId)
        if (userId == resolution.author.id) {
            return dailyCheckRepository.findByResolutionId(resolution)
                .map { DailyCheckResponse.from(it) }
        } else throw PermissionDeniedException()
    }

    @Transactional
    override fun updateDailyCheck(resolutionId: Long, userId: Long, dailyCheckId: Long, request: DailyCheckRequest)
            : DailyCheckResponse {
        val resolution = getByIdOrNull(resolutionId)
        val dailyCheck = dailyCheckRepository.findByIdOrNull(dailyCheckId)
            ?: throw ModelNotFoundException("DailyCheck", dailyCheckId)
        if (userId == resolution.author.id) {
            dailyCheck.updateDailyCheck(request.memo, resolution)
            return DailyCheckResponse.from(dailyCheck)
        } else throw PermissionDeniedException()
    }

    @Transactional
    override fun deleteDailyCheck(resolutionId: Long, dailyCheckId: Long, userId: Long) {
        val resolution = getByIdOrNull(resolutionId)

        if (userId == resolution.author.id) {
            dailyCheckRepository.deleteById(dailyCheckId)
        } else throw PermissionDeniedException()
    }

    fun getByIdOrNull(id: Long) = resolutionRepository.findByIdOrNull(id)
        ?: throw ModelNotFoundException("Resolution", id)
}