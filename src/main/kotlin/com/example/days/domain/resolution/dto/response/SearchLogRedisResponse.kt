package com.example.days.domain.resolution.dto.response

import com.example.days.domain.resolution.model.SearchLog
import java.io.Serializable

data class SearchLogRedisResponse(
    val title: String,
    val createdAt: String,
): Serializable
{
    constructor(): this("", "")
    companion object {
        fun from (search: SearchLog): SearchLogRedisResponse{
        return SearchLogRedisResponse(
               title = search.title,
               createdAt= search.createdAt
              )
         }
    }

}