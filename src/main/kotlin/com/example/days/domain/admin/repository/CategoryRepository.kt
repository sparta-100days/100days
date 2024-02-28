package com.example.days.domain.admin.repository

import com.example.days.domain.admin.model.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Long> {
}