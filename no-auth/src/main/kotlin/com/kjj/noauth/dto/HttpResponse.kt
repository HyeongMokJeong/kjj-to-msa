package com.kjj.noauth.dto

data class HttpResponse<T>(
    val data: T,
    val message: String
) {
}