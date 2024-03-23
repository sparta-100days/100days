package com.example.days.domain.user.repository

import com.example.days.domain.oauth.model.QSocialUser
import com.example.days.domain.user.model.QUser
import com.example.days.domain.user.model.User
import com.example.days.global.infra.queryDSL.QueryDslSupport
import org.springframework.stereotype.Repository

@Repository
class QueryDslUserRepository : QueryDslSupport() {

    private val user = QUser.user
    private val socialUser = QSocialUser.socialUser

    fun searchUserByNickname(nickname: String): List<User> {
        return queryFactory.selectFrom(user)
            .where(user.nickname.containsIgnoreCase(nickname))
            .fetch()
    }

    fun userWithSocialUser(id: Long): List<User> {
        return queryFactory.selectFrom(user)
            .join(user.socialUser, socialUser)
            .fetchJoin()
            .where(socialUser.id.eq(id))
            .fetch()
    }
}