package com.example.days.domain.admin.dto.response

import com.example.days.domain.admin.model.Category

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
