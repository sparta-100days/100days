package com.example.days.domain.category.dto.response

import com.example.days.domain.category.model.Category

data class CategoryResponse(
    val id: Long?,
    val name: String,
    val info: String
) {
    companion object {
        fun from(category: Category): CategoryResponse {
            return CategoryResponse(
                id = category.id!!,
                name = category.name,
                info = category.info
            )
        }
    }
}
