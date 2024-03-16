package com.example.days.domain.resolution.dto.response

import com.example.days.domain.resolution.model.SearchLog
import java.io.Serializable

data class SearchLogSearchResponse(
    val title: String,
    val createdAt: String,
): Serializable
{
    constructor(): this("", "")
    companion object {
        fun from (search: SearchLog): SearchLogSearchResponse{
        return SearchLogSearchResponse(
               title = search.title,
               createdAt= search.createdAt
              )
         }
    }

}