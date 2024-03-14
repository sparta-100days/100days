package com.example.days.domain.resolution.service

import com.example.days.domain.resolution.dto.response.SearchLogRedis
import com.example.days.domain.resolution.dto.response.SearchResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ResolutionRedisService {
    fun searchByResolution(title: String, pageable: Pageable): Page<SearchResponse>

    fun saveRecentSearchLog(title: String, userId: Long)

    fun findRecentSearchLog(userId: Long): List<SearchLogRedis>?

    fun deleteRecentSearchLog(userId: Long, title: String)

}