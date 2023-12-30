package com.kjj.user.client

import com.kjj.user.dto.menu.MenuNameAndCostDto
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import java.util.Map

@Service
class MenuClient (
    val restTemplate: RestTemplate,
    val uriTool: ClientUriTool
){
    fun existsById(menuId: Long): Boolean {
        val uri = uriTool.getRequestUri(
            "/v1/client/menu/exist",
            mapOf(Pair("menuId", menuId.toString()))
        )
        return restTemplate.getForObject(uri, Boolean::class)
    }

    fun findNameAndCostById(menuId: Long): MenuNameAndCostDto {
        val uri: String = uriTool.getRequestUri(
            "/v1/client/menu/name-cost",
            mapOf(Pair("menuId", menuId.toString())))
        return restTemplate.getForObject(uri, MenuNameAndCostDto::class)!!
    }

    fun findNameById(menuId: Long): String {
        val uri: String = uriTool.getRequestUri(
            "/v1/client/menu/name",
            mapOf(Pair("menuId", menuId.toString()))
        )
        return restTemplate.getForObject(uri, String::class)!!
    }
}