package com.kjj.noauth.config

import org.jasypt.encryption.StringEncryptor
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JasyptConfig {
    @Bean("jasypt")
    fun stringEncryptor(): StringEncryptor {
        val encryptor = PooledPBEStringEncryptor()
        val config = SimpleStringPBEConfig()
        config.setPassword(System.getProperty("password"))
        config.algorithm = "PBEWithMD5AndDES"
        config.poolSize = 1
        encryptor.setConfig(config)
        return encryptor
    }
}