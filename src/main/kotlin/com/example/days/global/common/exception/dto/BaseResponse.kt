package com.example.days.global.common.exception.dto

import com.example.days.global.common.exception.status.ResultCode


// ㅇㅅㅇ 이는 스트링으로 에러 처리를 할때 반환해주는 에러 반응이므로 추가로 작성해주었다.
data class BaseResponse<T>(
    val resultCode: String = ResultCode.SUCCESS.name,
    val data: T? = null,
    val message: String = ResultCode.SUCCESS.msg,
)
