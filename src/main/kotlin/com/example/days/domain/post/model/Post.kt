package com.example.days.domain.post.model

import com.example.days.domain.post.dto.request.PostRequest
import com.example.days.global.entity.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.SQLRestriction

@Entity
@Table(name = "post")
//@SQLRestriction("is_deleted <> false")
class Post (
    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "content", nullable = false)
    var content: String,

    @Column(name = "image_Url")
    var image_Url : String,

//    @OneToMany( cascade = [CascadeType.ALL], orphanRemoval = true,fetch = FetchType.LAZY)
//    var comments: MutableList<Comment> = mutableListOf(),

    @Column(name ="PostType")
    @Enumerated(EnumType.STRING)
    var postType: PostType

) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    var id: Long? = null

    fun updatePost(request: PostRequest) {
        title = request.title
        content = request.content
        image_Url = request.image_Url
        postType = request.postType
    }

    /*fun deletePost() {
        isDeleted = false
    }*/
}