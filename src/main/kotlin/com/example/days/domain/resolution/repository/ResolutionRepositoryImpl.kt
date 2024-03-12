package com.example.days.domain.resolution.repository

import com.example.days.domain.resolution.model.QResolution
import com.example.days.domain.resolution.model.QResolutionRanking
import com.example.days.domain.resolution.model.Resolution
import com.example.days.global.common.SortOrder
import com.example.days.global.infra.queryDSL.QueryDslSupport
import com.querydsl.core.BooleanBuilder
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class ResolutionRepositoryImpl: QueryDslSupport(), QueryResolutionRepository {
    private val resolution = QResolution.resolution
    override fun findByPageable(page: Int, sortOrder: SortOrder?): Page<Resolution> {
        val whereClause = BooleanBuilder()
        val pageable: PageRequest = PageRequest.of(page, 10, Sort.by(Sort.Direction.ASC, sortOrder.toString()))
        val totalCount = queryFactory.select(resolution.count()).from(resolution).where(whereClause).fetchOne() ?: 0L

        val query = queryFactory.selectFrom(resolution)
            .where(whereClause)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
        if (pageable.sort.isSorted){
            when(pageable.sort.first()?.property){
                "LIKE" -> query.orderBy(resolution.likeCount.desc())
                "CREATED_AT" -> query.orderBy(resolution.createdAt.asc())
                else -> query.orderBy(resolution.id.asc())
            }
        }

        val contents = query.fetch()
        return PageImpl(contents, pageable, totalCount)
    }

    @Transactional
    override fun getResolutionRanking(): List<Resolution> {
        val query = queryFactory
            .selectFrom(resolution)
            .limit(10)
            .orderBy(resolution.likeCount.desc())
            .fetch()

        return query
    }
}