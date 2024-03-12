package com.example.days.domain.resolution.repository

import com.example.days.domain.resolution.model.Resolution
import com.example.days.global.common.SortOrder
import org.springframework.data.domain.Page

interface QueryResolutionRepository {
    fun findByPageable(page: Int, sortOrder: SortOrder?): Page<Resolution>

    fun getResolutionRanking(): List<Resolution>
}