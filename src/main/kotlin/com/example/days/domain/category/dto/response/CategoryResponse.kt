package com.example.days.domain.category.dto.response

import com.example.days.domain.category.model.Category

data class CategoryResponse(
    val id: Long?
) {
    companion object {
        fun from(category: Category): CategoryResponse {
            return CategoryResponse(
                id = category.id!!
            )
        }
    }
}
