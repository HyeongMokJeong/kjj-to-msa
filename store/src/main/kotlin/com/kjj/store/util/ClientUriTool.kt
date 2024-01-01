package com.kjj.store.util

import org.springframework.stereotype.Component

@Component
class ClientUriTool {
    fun getRequestURI(host: String, path: String): String {
        return host + path
    }

    fun getRequestURI(host: String, path: String, params: Map<String, String?>): String {
        val stringBuilder = StringBuilder("$host$path?")
        for ((key, value) in params) {
            stringBuilder.append("$key=$value&")
        }
        return stringBuilder.toString()
    }
}