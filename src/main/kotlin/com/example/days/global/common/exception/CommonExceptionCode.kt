package com.example.days.global.common.exception

import org.springframework.http.HttpStatus

enum class CommonExceptionCode(
    val status: HttpStatus,
    val message: String
){
    ENTITY_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "존재하지 않는 %s입니다. id => %d"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예상치 못한 서버 오류입니다.")
}