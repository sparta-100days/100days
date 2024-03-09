package com.example.days.domain.report.repository

import com.example.days.domain.report.model.QUserReport
import com.example.days.domain.report.model.UserReport
import com.example.days.global.infra.queryDSL.QueryDslSupport
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import com.querydsl.core.BooleanBuilder
import org.springframework.data.domain.PageImpl

@Repository
class ReportRepositoryImpl : AReportRepository, QueryDslSupport() {

    private val report = QUserReport.userReport

    override fun findByPageableAndNickname(pageable: Pageable, nickname: String?): Page<UserReport> {
        val whereClause = BooleanBuilder()

        nickname?.let { whereClause.and(report.reportedUserId.nickname.eq(nickname)) }

        val totalCount = queryFactory.select(report.count()).from(report).where(whereClause).fetchOne() ?: 0L

        val query = queryFactory.selectFrom(report)
            .where(whereClause)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())

        if (pageable.sort.isSorted) {
            // 정렬 기준 설정
            when (pageable.sort.first()?.property) {
                "id" -> query.orderBy(report.id.asc())
                "nickname" -> query.orderBy(report.reportedUserId.nickname.asc())
                else -> query.orderBy(report.id.asc())
            }
        } else {
            query.orderBy(report.id.asc())
        }

        // 최종적으로 쿼리 수행
        val contents = query.fetch()

        // Page 구현체 반환
        return PageImpl(contents, pageable, totalCount)

    }
}
