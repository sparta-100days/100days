package com.example.days.domain.resolution.model

import com.example.days.domain.resolution.dto.request.ResolutionRequest
import com.example.days.domain.resolution.dto.response.ResolutionResponse
import com.example.days.domain.user.model.Users
import com.example.days.global.entity.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDateTime

@Entity
@Table(name = "resolutions")
class Resolution(
    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "description", nullable = false)
    var description: String,

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "author")
    val author: Users,

    @Column(name = "complete_status")
    val completeStatus: Boolean = false,

    @Column(name = "daily_status")
    val dailyStatus: Boolean = false,

    @Column(name = "progress")
    val progress: Long = 0,

    // ^오^: 카테고리를 목적 페이지와 연관관계를 굳이 지어야 하는지에 대해 의문입니다.
    @Column(name = "category")
    var category: String,

    @Column(name = "like_count")
    val likeCount: Long = 0
): BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aim_id")
    val id: Long? = null

    @Column(name = "deadline")
    val deadline: LocalDateTime = createdAt.plusDays(100)

    companion object {
        fun of(request: ResolutionRequest, user: Users) =
            Resolution(
                title = request.title,
                description = request.description,
                category = request.category,
                author = user.name
            )
    }

    fun from() = ResolutionResponse(
        id = id,
        title = title,
        description = description,
        completeStatus = completeStatus,
        dailyStatus = dailyStatus,
        category = category,
        likeCount = likeCount,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

    fun updateResolution(request: ResolutionRequest){
        title = request.title
        description = request.description
        category = request.category
    }
}

