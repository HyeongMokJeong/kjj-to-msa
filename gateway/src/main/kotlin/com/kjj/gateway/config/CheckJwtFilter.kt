package com.kjj.gateway.config

import com.kjj.gateway.jwt.JwtUtil
import lombok.extern.slf4j.Slf4j
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

@Slf4j
@Component
class CheckJwtFilter(
    val jwtUtil: JwtUtil
): AbstractGatewayFilterFactory<Any>() {

    private fun checkJwt(
        token: String?,
        username: String?,
        id: String?,
        uri: String
    ): Boolean {
        val managerApiPrefix = "/api/manager"
        val userRoles = "ROLE_USER"
        val managerRoles = "ROLE_MANAGER"

        if (token == null) {
            return false
        }

        // roles 추출
        val roles: String = jwtUtil.getRolesFromToken(token)

        // 부족하면 false
        if (username == null || id == null) {
            return false
        }

        // 권한이 다르면 false
        if (roles != managerRoles && roles != userRoles) {
            return false
        }

        // 권한이 맞지 않으면 false
        if (uri.startsWith(managerApiPrefix) && roles != managerRoles) {
            return false
        }

        return true
    }

    private fun addQueryParamToUri(
        uri: String,
        params: MultiValueMap<String, String>,
        username: String,
        id: String
    ): URI {
        // 쿼리 파라미터 username, userId 추가
        val idParamKey = "userId"
        val usernameParamKey = "username"
        val newParams: MultiValueMap<String, String> = LinkedMultiValueMap()

        newParams.add(idParamKey, id)
        newParams.add(usernameParamKey, username)
        newParams.addAll(params)

        val uriBuilder = UriComponentsBuilder.fromUriString(uri)
            .queryParams(newParams)
        return uriBuilder.build().toUri()
    }

    override fun apply(config: Any): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            val token: String? = exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION)

            // token이 null이면 400
            if (token == null) {
                exchange.response.statusCode = HttpStatus.BAD_REQUEST
                return@GatewayFilter exchange.response.setComplete()
            }
            // 만료되었으면 401
            if (jwtUtil.isTokenExpired(token)) {
                exchange.response.statusCode = HttpStatus.UNAUTHORIZED
                return@GatewayFilter exchange.response.setComplete()
            }

            // 유저명, 아이디 추출
            val username = jwtUtil.getUsernameFromToken(token)
            val id = jwtUtil.getIdFromToken(token)

            // request uri 추출
            val requestUri = exchange.request.uri.path

            // 토큰 유효성 검사에 실패하면 403
            if (!checkJwt(token, username, id, requestUri)) {
                exchange.response.statusCode = HttpStatus.FORBIDDEN
                return@GatewayFilter exchange.response.setComplete()
            }

            // 쿼리 파라미터 추가한 새로운 URI
            val uri = addQueryParamToUri(requestUri, exchange.request.queryParams, username, id)

            val webExchange = exchange.mutate()
                .request(exchange.request.mutate().uri(uri).build())
                .build()

            chain.filter(webExchange)
        }
    }
}