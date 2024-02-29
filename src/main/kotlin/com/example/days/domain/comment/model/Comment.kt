package com.example.days.domain.comment.model

import com.example.days.domain.comment.dto.request.CommentRequest
import com.example.days.domain.post.model.Post
import com.example.days.global.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name="comment")
class Comment(
    @Column(name = "comment", nullable = false)
    var comment: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    val post: Post

) : BaseEntity(){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    var id: Long? = null

    fun updateComment(request: CommentRequest) {
        comment = request.comment
    }

    /*fun deleteComment() {
        isDeleted = true
    }*/
}