package com.example.days.domain.resolution.model

import com.example.days.domain.category.model.Category
import com.example.days.domain.user.model.User
import com.example.days.global.entity.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDate
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
    val author: User,

    @Column(name = "complete_status")
    var completeStatus: Boolean = false,

    @Column(name = "daily_status")
    var dailyStatus: Boolean = false,

    @Column(name = "progress")
    var progress: Long = 0,

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "category")
    var category: Category,

    @Column(name = "like_count")
    var likeCount: Long = 0
): BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resolution_id")
    val id: Long? = null

    @Column(name = "deadline")
    val deadline: LocalDate = createdAt.toLocalDate().plusDays(100)

    fun updateResolution(updatedTitle: String, updatedDescription: String, updatedCategory: Category){
        title = updatedTitle
        description = updatedDescription
        category = updatedCategory
    }

    fun updateLikeCount(b: Boolean){
        if(b) likeCount += 1 else likeCount -=1
    }

    fun updateProgress(){
        dailyStatus = true
        progress += 1
        if(progress == 100L){
            completeStatus = true
        }
    }
}

