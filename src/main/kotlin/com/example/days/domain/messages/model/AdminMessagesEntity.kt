package com.example.days.domain.messages.model

import com.example.days.domain.admin.model.Admin
import com.example.days.domain.user.model.User
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@Entity
@Table(name = "messageByAdmin")
class AdminMessagesEntity(

    @Column(name = "title", nullable = false)
    val title: String,

    @Column(name = "content", nullable = false)
    val content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    val admin: Admin,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    val receiver: User,

    @Column(name = "deletedByReceiver", nullable = false)
    var deletedByReceiver: Boolean = false,

    @Column(name = "readStatus", nullable = false)
    var readStatus: Boolean = false

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @CreatedDate
    @Column(nullable = false, updatable = false)
    var sentAt: LocalDateTime = LocalDateTime.now()

    fun deletedByReceiver() {
        deletedByReceiver = true
    }

    fun readStatus() {
        readStatus = true
    }
}