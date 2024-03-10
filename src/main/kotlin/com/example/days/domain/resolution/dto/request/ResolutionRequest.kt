package com.example.days.domain.resolution.dto.request

import com.example.days.domain.category.model.Category
import com.example.days.domain.resolution.model.Resolution
import com.example.days.domain.user.model.User
import jakarta.validation.constraints.NotBlank

data class ResolutionRequest(
    @field:NotBlank(message = "진행할 목표를 입력해주세요.")
    val title: String,
    @field:NotBlank(message = "목표에 대한 설명을 입력해주세요")
    val description: String,
    // ^오^: 카테고리를 선택하는 방법은 좀 더 생각해 볼 필요가 있을거같습니다. 일단은 String 형태로 사용자가 직접 쓰게 했는데
    // 카테고리 관리는 백오피스 쪽이다보니  그쪽과 연계가 되야 할 것 같습니다.
    @field:NotBlank(message = "카테고리를 선택해주세요.")
    val category: String

){
    companion object{
        fun of(request: ResolutionRequest,category: Category, user: User) = Resolution(
            title = request.title,
            description = request.description,
            category = category,
            author = user
        )
    }
}

