package com.kjj.apigateway.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@Order(1)
public class AddIdToHeaderFilter extends AbstractGatewayFilterFactory {
    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            // request id 생성
            String requestId = UUID.randomUUID().toString();

            // header에 request id 추가
            exchange.getRequest().mutate().headers(httpHeaders -> httpHeaders.add("X-Request-ID", requestId));

            // logback에서 사용하기 위해 MDC에 request id 추가
            MDC.put("requestId", requestId);

            return chain.filter(exchange);
        };
    }
}