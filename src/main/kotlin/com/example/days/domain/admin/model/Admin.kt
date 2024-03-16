package com.example.days.domain.admin.model

import com.example.days.domain.admin.repository.AdminRepository
import com.example.days.domain.user.model.Status
import com.example.days.domain.user.model.UserRole
import com.example.days.global.common.exception.common.NicknameExistException
import com.example.days.global.common.exception.user.NoSearchUserByEmailException
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@Entity
@Table(name = "admin")
class Admin(

    @Column(name = "nickname") val nickname: String,

    @Column(name = "email") val email: String,

    @Column(name = "password") val password: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "status") var status: Status,

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    val role: UserRole,

    ) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
    fun adminBanByAdmin() {
        status = Status.BAN
    }

    //ㅇㅅㅇ baseEntity를 사용하려 했으나 이는 논리적 삭제는 따로 상태 name에 추가해주었고 updatedAt이 아닌 deletedAt을 해야하므로 안함.
    @CreatedDate
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()

    @Column(nullable = true)
    var deletedAt: LocalDateTime? = null

}

fun checkingEmailAndNicknameExists(email: String, nickname: String, adminRepository: AdminRepository) {
    if (adminRepository.existsByEmail(email)) {
        throw NoSearchUserByEmailException(email)
    }

    if (adminRepository.existsByNickname(nickname)) {
        throw NicknameExistException(nickname)
    }
}