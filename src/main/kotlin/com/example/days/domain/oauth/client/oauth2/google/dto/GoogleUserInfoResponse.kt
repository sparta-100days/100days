package com.example.days.domain.oauth.client.oauth2.google.dto
//
//import com.example.days.domain.oauth.client.oauth2.OAuth2LoginUserInfo
//import com.example.days.domain.oauth.model.OAuth2Provider
//import com.fasterxml.jackson.databind.PropertyNamingStrategy
//import com.fasterxml.jackson.databind.annotation.JsonNaming
//
//@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
//class GoogleUserInfoResponse(
//    id: Long,
//    properties: GoogleUserPropertiesResponse
//) : OAuth2LoginUserInfo(
//    provider = OAuth2Provider.GOOGLE,
//    id = id.toString(),
//    email = properties.email,
//    nickname = properties.nickname
//)