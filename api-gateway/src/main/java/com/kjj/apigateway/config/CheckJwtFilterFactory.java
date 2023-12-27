package com.kjj.apigateway.config;

import com.kjj.apigateway.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@Component
@Slf4j
public class CheckJwtFilterFactory extends AbstractGatewayFilterFactory {

    private final JwtUtil jwtUtil;

    private boolean checkJwt(String token, String username, String id, String uri) {
        String managerApiPrefix = "/api/manager";
        String userRoles = "ROLE_USER";
        String managerRoles = "ROLE_MANAGER";
        if (token == null) {
            log.info("token is null");
            return false;
        }

        // roles 추출
        String roles = jwtUtil.getRolesFromToken(token);

        // 부족하면 false
        if (username == null || id == null || roles == null) {
            log.info("field is null - username = {username} / id = {id} / roles = {roles}");
            return false;
        }

        // 권한이 다르면 false
        if (!roles.equals(managerRoles) && !roles.equals(userRoles)) {
            log.info("roles not match - roles = {roles}");
            return false;
        }

        // 권한이 맞지 않으면 false
        if (uri.startsWith(managerApiPrefix) && !roles.equals(managerRoles)) {
            log.info("roles not match - roles = {roles}");
            return false;
        }

        return true;
    }

    private URI addQueryParamToUri(String uri, MultiValueMap<String, String> params, String username, String id) {
        // 쿼리 파라미터 username, userId 추가
        String idParamKey = "userId";
        String usernameParamKey = "username";

        MultiValueMap<String, String> newParams = new LinkedMultiValueMap<>();
        newParams.add(idParamKey, id);
        newParams.add(usernameParamKey, username);

        newParams.addAll(params);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(uri)
                .queryParams(newParams);

        return uriBuilder.build().toUri();
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            // request header에서 token 추출
            String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            // 토큰이 만료된 상태라면 401 응답
            if (jwtUtil.isTokenExpired(token)) {
                log.info("token expired");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            // token에서 username, id 추출
            String username = jwtUtil.getUsernameFromToken(token);
            String id = jwtUtil.getIdFromToken(token);

            // request에서 requestUri, 쿼리 파라미터 추출
            String requestUri = exchange.getRequest().getURI().getPath();

            // 토큰 유효성 검사에 실패하면 403 응답
            if (!checkJwt(token, username, id, requestUri)) {
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                return exchange.getResponse().setComplete();
            }

            // 이전 쿼리 파라미터에 username과 id를 추가한 새로운 uri
            URI uri = addQueryParamToUri(requestUri, exchange.getRequest().getQueryParams(), username, id);

            ServerWebExchange webExchange = exchange.mutate()
                    .request(exchange.getRequest().mutate().uri(uri).build())
                    .build();

            return chain.filter(webExchange);
        };
    }
}