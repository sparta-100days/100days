package com.example.days.domain.resolution.repository

import com.example.days.domain.resolution.model.Resolution
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime

interface ResolutionRepository: JpaRepository<Resolution, Long>, QueryResolutionRepository{
    @Modifying
    @Transactional
    @Query("UPDATE Resolution r SET r.dailyStatus = false")
    fun resetResolutionDailyStatus2()

    @Modifying
    @Transactional
    @Query("UPDATE Resolution r SET r.completeStatus = true WHERE r.deadline <= :today")
    // 테스트 시
//    fun checkResolutionDeadline(@Param("today") today: LocalDateTime)
    fun checkResolutionDeadline(@Param("today") today: LocalDate)
}