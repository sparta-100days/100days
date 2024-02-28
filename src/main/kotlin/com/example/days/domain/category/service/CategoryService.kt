package com.example.days.domain.category.service

import com.example.days.domain.category.dto.request.CategoryRequest
import com.example.days.domain.category.dto.request.CategoryUpdateRequest
import com.example.days.domain.category.dto.response.CategoryResponse

interface CategoryService {

    fun getCategoryList(): List<CategoryResponse>

    // 카테고리 추가 기능, 조회는 admin에 넣는 것이 아닌것 같아서 나중에 다른 곳에 추가할 필요 있을듯?
    fun createCategory(req: CategoryRequest): CategoryResponse

    // 카테고리 수정 기능
    fun updateCategory(categoryId: Long, req: CategoryUpdateRequest): CategoryResponse

    // 카테고리 삭제 기능
    fun deleteCategory(categoryId: Long)
}