package com.example.days.domain.user.model

import com.example.days.domain.oauth2.model.OAuth2Provider
import com.example.days.domain.user.dto.request.ModifyInfoRequest
import com.example.days.global.entity.BaseEntity
import com.example.days.global.infra.regex.RegexFunc
import com.example.days.global.infra.security.PasswordEncoderConfig
import com.example.days.global.support.RandomCode
import jakarta.persistence.*
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "users")
class User(

    @Column(name = "email")
    var email: String,

    @Column(name = "nickname")
    var nickname: String,

    @Column(name = "password")
    var password: String,

    @Column(name = "birth")
    var birth: LocalDate,

    @Column(name = "isdelete")
    var isDelete: Boolean,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    var status: Status,

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    val role: UserRole,

    @Column(name = "count_report") var countReport: Int = 0,

    // 고유 id
    @Column(name = "account_id")
    val accountId: String,

    // social login ID
    @Enumerated(EnumType.STRING)
    @Column(name = "provider")
    val provider: OAuth2Provider?,

    @Column(name = "provider_id")
    val providerId: String,

    ) : BaseEntity() {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "period")
    var period: LocalDate? = null

    fun userDeleteByAdmin() {
        status = Status.WITHDRAW
    }

    fun userBanByAdmin() {
        status = Status.BAN
    }

    fun userIsDeletedByAdmin() {
        isDelete = true
    }

    fun updateUser(request: ModifyInfoRequest) {
        nickname = request.nickname
        birth = request.birth
    }

    companion object {
        // User.of(id, provider) 형식으로 사용 가능하게 고침
        fun of(id: String, provider: OAuth2Provider): User {

            val user = User(
                email = id,
                nickname = "익명",
                password =  RandomCode(RegexFunc()).generateRandomCode(10),
                birth = LocalDate.now(),
                accountId = RandomCode(RegexFunc()).generateRandomCode(12),
                isDelete = false,
                status = Status.ACTIVE,
                role = UserRole.USER,
                provider = provider,
                providerId = id
            )

            return when (provider) {
                OAuth2Provider.KAKAO -> {
                    OAuth2Provider.KAKAO.name
                    user
                }

                OAuth2Provider.GOOGLE -> {
                    OAuth2Provider.GOOGLE.name
                    user
                }
            }
        }
    }
}




