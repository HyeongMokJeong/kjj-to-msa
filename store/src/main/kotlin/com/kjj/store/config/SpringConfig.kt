package com.kjj.store.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class SpringConfig {
    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }
}