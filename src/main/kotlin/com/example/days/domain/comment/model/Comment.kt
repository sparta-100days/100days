package com.example.days.domain.comment.model

import com.example.days.domain.post.model.Post
import com.example.days.domain.user.model.User
import com.example.days.global.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "comments")
class Comment(

    @Column(name = "comment", nullable = false)
    var comment: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    val postId: Post,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val userId: User,

    ) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null

}