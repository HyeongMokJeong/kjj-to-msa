package com.kjj.gateway.config

import org.slf4j.MDC
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.util.*

@Component
@Order(1)
class AddIdToHeaderFilter: AbstractGatewayFilterFactory<Any>() {
    override fun apply(config: Any): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            val requestId = UUID.randomUUID().toString()

            exchange.request.mutate().headers { httpHeaders -> httpHeaders.add("X-Request-ID", requestId) }

            MDC.put("requestId", requestId)

            chain.filter(exchange)
        }
    }
}