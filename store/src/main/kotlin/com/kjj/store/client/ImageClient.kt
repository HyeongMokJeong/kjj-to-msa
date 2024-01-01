package com.kjj.store.client

import com.kjj.store.util.ClientUriTool
import com.kjj.store.util.HttpTool
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.patchForObject
import org.springframework.web.client.postForObject
import org.springframework.web.multipart.MultipartFile

@Service
class ImageClient(
    val restTemplate: RestTemplate,
    val httpTool: HttpTool,
    val clientUriTool: ClientUriTool
) {
    @Value("\${my.client.image.host}")
    lateinit var imageServerHost: String

    fun uploadImage(file: MultipartFile, dir: String): String? {
        return restTemplate.postForObject(
            clientUriTool.getRequestURI(imageServerHost, "/v1/image"),
            httpTool.getRequestEntityTypeFormData(file, dir),
            String::class
        )
    }

    fun updateImage(file: MultipartFile, dir: String) {
        restTemplate.patchForObject(
            clientUriTool.getRequestURI(imageServerHost, "/v1/image"),
            httpTool.getRequestEntityTypeFormData(file, dir),
            Unit::class.java
        )
    }
}