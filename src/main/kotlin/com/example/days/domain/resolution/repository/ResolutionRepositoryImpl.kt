package com.example.days.domain.resolution.repository

import com.example.days.domain.resolution.dto.response.SearchResponse
import com.example.days.domain.resolution.model.QResolution
import com.example.days.domain.resolution.model.Resolution
import com.example.days.global.common.SortOrder
import com.example.days.global.infra.queryDSL.QueryDslSupport
import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Expression
import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.EntityPathBase
import com.querydsl.core.types.dsl.PathBuilder
import org.springframework.data.domain.*
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
    override fun getResolutionRanking(): List<Resolution> {
        return queryFactory.selectFrom(resolution)
            .limit(10)
            .orderBy(resolution.likeCount.desc())
            .fetch()
    }

    override fun searchByTitle(
        title: String,
        pageable: Pageable
    ): Page<SearchResponse> {
        val whereClause = BooleanBuilder()
        whereClause.and(resolution.title.contains(title))

        val totalCount = queryFactory
            .select(resolution.count())
            .from(resolution)
            .where(whereClause)
            .fetchOne() ?: 0L

        val contents = queryFactory
            .select(
                Projections.constructor(
                    SearchResponse::class.java,
                    resolution.id,
                    resolution.title
                )
            )
            .from(resolution)
            .where(whereClause)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .orderBy(*getOrderSpecifier(pageable, resolution))
            .fetch()

        return PageImpl(contents, pageable, totalCount)

    }

    private fun getOrderSpecifier(
        pageable: Pageable,
        path: EntityPathBase<*>
    ): Array<OrderSpecifier<*>>{
        val pathBuilder = PathBuilder(path.type, path.metadata)

        return pageable.sort.toList().map {
                order -> OrderSpecifier(
            if(order.isAscending) Order.ASC else Order.DESC,
            pathBuilder.get(order.property) as Expression<Comparable<*>>
        )
        }.toTypedArray()
    }
}