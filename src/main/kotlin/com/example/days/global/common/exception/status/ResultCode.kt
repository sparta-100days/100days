package com.example.days.global.common.exception.status

// ㅇㅅㅇ 이는 스트링으로 에러 처리를 할때 반환해주는 이넘클래스이므로 추가로 작성해주었다.
enum class ResultCode(val msg: String) {
    SUCCESS("정상"),
    ERROR("에러")
}