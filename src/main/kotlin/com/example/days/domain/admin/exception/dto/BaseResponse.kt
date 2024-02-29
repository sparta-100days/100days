package com.example.days.domain.admin.exception.dto

import com.example.days.domain.admin.exception.status.ResultCode

data class BaseResponse<T>(
    val resultCode: String = ResultCode.SUCCESS.name,
    val data: T? = null,
    val message: String = ResultCode.SUCCESS.msg,
)
