package com.kjj.user.dto.response

data class ResponseExceptionTemplate(
    val date: String,
    val message: String?,
    val uri: String,
    val status: Int
) {
}