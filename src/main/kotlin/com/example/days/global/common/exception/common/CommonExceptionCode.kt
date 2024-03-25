package com.example.days.global.common.exception.common

import org.springframework.http.HttpStatus

enum class CommonExceptionCode(
    val httpStatus: HttpStatus,
    val message: String
){
    ENTITY_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "존재하지 않는 %s입니다. id => %d"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예상치 못한 서버 오류입니다."),
    LIKE_ALREADY_PROCESSED_ERROR(HttpStatus.CONFLICT, "이미 처리된 요청입니다."),
    CHECK_ALREADY_COMPLETED_ERROR(HttpStatus.CONFLICT, "이미 오늘 체크를 완료했습니다."),
    RESOLUTION_ALREADY_COMPLETED_ERROR(HttpStatus.CONFLICT, "이미 완료된 목표입니다."),
    TYPE_NOT_FOUND_ERROR(HttpStatus.CONFLICT, "게시글 타입이 일치하지 않습니다.")
}