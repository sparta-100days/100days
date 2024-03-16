package com.example.days.domain.resolution.service

import com.example.days.domain.category.repository.CategoryRepository
import com.example.days.domain.resolution.dto.request.ResolutionRequest
import com.example.days.domain.resolution.dto.response.ResolutionResponse
import com.example.days.domain.resolution.dto.response.SimpleResolutionResponse
import com.example.days.domain.resolution.model.Resolution
import com.example.days.domain.resolution.repository.ResolutionRepository
import com.example.days.domain.user.repository.UserRepository
import com.example.days.global.common.SortOrder
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.domain.Page
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate


@Service
class ResolutionServiceImpl(
    private val resolutionRepository: ResolutionRepository,
    private val userRepository: UserRepository,
    private val categoryRepository: CategoryRepository,
    private val redisTemplate: RedisTemplate<Any, SimpleResolutionResponse>,
) : ResolutionService {
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
        if (updatedResolution.author.id == userId) {
            updatedResolution.updateResolution(request.title, request.description, category)
            return ResolutionResponse.from(updatedResolution)
        } else TODO("예외처리")

    }

    @Transactional
    override fun deleteResolution(resolutionId: Long, userId: Long) {
        val resolution = getByIdOrNull(resolutionId)
        if (resolution.author.id == userId) {
            resolutionRepository.delete(resolution)
        } else TODO("예외처리")
    }

    // 테스트 시 ( 3분에 한번 동작 )
//    @Scheduled(fixedRate = 180000)
    @Scheduled(cron = "0 0 0 * * *")
    fun resetResolutionStatus() {
        // ^오^
        // resetResolutionDailyStatus, resetResolutionDailyStatus2 이렇게 2가지 버젼이 있습니다.
        // 첫번째 건 queryDSL, 두번째건 JPQL 을 이용한 메서드입니다.
        // 어떤 방법이 이득인지 고민입니다.
        resolutionRepository.resetResolutionDailyStatus2()
        // 테스트 시
//        val today: LocalDateTime = LocalDateTime.now()
        val today: LocalDate = LocalDate.now()
        resolutionRepository.checkResolutionDeadline(today)
    }

    override fun getResolutionRanking(): List<SimpleResolutionResponse> {
        val resolutionRanking = redisTemplate.opsForList().range("ranking", 0, -1)
        val objectMapper = ObjectMapper()

        return resolutionRanking?.map {
            objectMapper.readValue(it.toString(), SimpleResolutionResponse::class.java)
        } ?: emptyList()
//        return resolutionRanking ?: emptyList()
    }

    fun getByIdOrNull(id: Long) = resolutionRepository.findByIdOrNull(id) ?: TODO("예외처리 구현예정")
}