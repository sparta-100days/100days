package com.example.days.domain.report.model

import com.example.days.domain.comment.model.Comment
import com.example.days.domain.post.model.Post
import com.example.days.domain.user.model.User
import com.example.days.global.entity.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
@Table(name = "postReport")
class PostReport(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    var reporter: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_post_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    var reportedPost: Post,

    @Column(name = "content")
    var content: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "reportStatus")
    val reportStatus: ReportStatus,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null
}