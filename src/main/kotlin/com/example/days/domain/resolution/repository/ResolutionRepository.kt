package com.example.days.domain.resolution.repository

import com.example.days.domain.resolution.model.Resolution
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface ResolutionRepository: JpaRepository<Resolution, Long>, QueryResolutionRepository{
    @Modifying
    @Transactional
    @Query("UPDATE Resolution r SET r.dailyStatus = false")
    fun resetResolutionDailyStatus2()
}