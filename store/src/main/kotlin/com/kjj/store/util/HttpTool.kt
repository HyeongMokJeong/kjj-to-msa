package com.kjj.store.util

import org.springframework.core.io.ByteArrayResource
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.multipart.MultipartFile

@Component
class HttpTool {
    fun getRequestEntityTypeFormData(file: MultipartFile, dir: String): HttpEntity<MultiValueMap<String, Any>> {
        val headers = HttpHeaders()
        headers.contentType = MediaType.MULTIPART_FORM_DATA

        val contentsAsResource: ByteArrayResource = object : ByteArrayResource(file.getBytes()) {
            override fun getFilename(): String {
                return file.name
            }
        }

        val params: MultiValueMap<String, Any> = LinkedMultiValueMap()
        params.add("file", contentsAsResource)
        params.add("dir", dir)

        return HttpEntity(params, headers)
    }
}