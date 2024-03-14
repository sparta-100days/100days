package com.example.days.domain.resolution.repository

import com.example.days.domain.resolution.model.Resolution
import org.springframework.data.jpa.repository.JpaRepository

interface ResolutionRepository: JpaRepository<Resolution, Long>, QueryResolutionRepository{
//    fun findByTitle(title: String): Resolution?
}