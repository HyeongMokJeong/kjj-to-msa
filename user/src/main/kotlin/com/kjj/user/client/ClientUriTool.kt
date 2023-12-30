package com.kjj.user.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class ClientUriTool {
    @Value("\${kjj.client.host}")
    lateinit var clientServerHost: String

    fun getRequestUri(path: String, params: Map<String, String>): String {
        val stringBuilder = StringBuilder("$clientServerHost$path?")
        for ((key, value) in params) {
            stringBuilder.append("$key=$value&")
        }
        return stringBuilder.toString()
    }
}