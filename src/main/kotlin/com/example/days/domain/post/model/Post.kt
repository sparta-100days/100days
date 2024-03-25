package com.example.days.domain.post.model

import com.example.days.domain.category.model.Category
import com.example.days.domain.comment.model.Comment
import com.example.days.domain.resolution.model.Resolution
import com.example.days.domain.user.model.User
import com.example.days.global.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "posts")
class Post(

    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "content", nullable = true)
    var content: String,

    @Column(name = "image_url", nullable = true)
    var imageUrl: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    val type: PostType,

    // id
    @ManyToOne
    @JoinColumn(name = "category_id")
    val categoryId: Category?,

    @ManyToOne
    @JoinColumn(name = "resolution_id")
    val resolutionId: Resolution?,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val userId: User?,

    // comment
    @OneToMany(mappedBy = "postId", cascade = [CascadeType.ALL], orphanRemoval = true)
    var comments: MutableList<Comment> = mutableListOf(),

    ) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null

}