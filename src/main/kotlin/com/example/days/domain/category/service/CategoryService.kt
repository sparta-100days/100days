package com.example.days.domain.category.service

import com.example.days.domain.category.dto.request.CategoryRequest
import com.example.days.domain.category.dto.request.CategoryUpdateRequest
import com.example.days.domain.category.dto.response.CategoryResponse

interface CategoryService {
    //ㅇㅅㅇ 카테고리를 테이블로 일단 나누기로 해서 추가함.
    // ㅇㅅㅇ 카테고리 전체 조회 기능, 이는 모든 사람이 조회할 수 있어야 함.
    fun getCategoryList(): List<CategoryResponse>

    // ㅇㅅㅇ 카테고리 추가 기능, 조회는 admin에 넣는 것이 아닌것 같아서 나중에 다른 곳에 추가할 필요 있을듯?
    fun createCategory(req: CategoryRequest): CategoryResponse

    // ㅇㅅㅇ 카테고리 수정 기능
    fun updateCategory(categoryId: Long, req: CategoryUpdateRequest): CategoryResponse

    // ㅇㅅㅇ 카테고리 삭제 기능
    fun deleteCategory(categoryId: Long)
}