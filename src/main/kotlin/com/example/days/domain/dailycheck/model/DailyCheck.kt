package com.example.days.domain.dailycheck.model

import com.example.days.domain.dailycheck.dto.request.DailyCheckRequest
import com.example.days.domain.resolution.model.Resolution
import com.example.days.global.entity.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
@Table(name = "dailycheck")
class DailyCheck (
    @Column(name = "memo")
    var memo: String,

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "resolution_id")
    var resolutionId: Resolution
): BaseEntity(){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memo_id")
    val id: Long? = null

    fun updateDailyCheck(updatedMemo: String, resolution: Resolution) {
        memo = updatedMemo
        resolutionId = resolution
    }


}