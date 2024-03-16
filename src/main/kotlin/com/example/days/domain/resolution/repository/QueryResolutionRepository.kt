package com.example.days.domain.resolution.repository

import com.example.days.domain.resolution.dto.response.SearchResponse
import com.example.days.domain.resolution.model.Resolution
import com.example.days.global.common.SortOrder
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface QueryResolutionRepository {
    fun findByPageable(page: Int, sortOrder: SortOrder?): Page<Resolution>
    fun getResolutionRanking(): List<Resolution>

    fun searchByTitle(
        title: String,
        pageable: Pageable
    ): Page<SearchResponse>
}