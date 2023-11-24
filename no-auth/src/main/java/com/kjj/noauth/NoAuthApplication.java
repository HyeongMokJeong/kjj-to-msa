package com.kjj.noauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class NoAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoAuthApplication.class, args);
    }

}
