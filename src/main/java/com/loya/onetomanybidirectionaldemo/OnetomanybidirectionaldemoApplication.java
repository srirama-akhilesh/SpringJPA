package com.loya.onetomanybidirectionaldemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.loya.onetomanybidirectionaldemo.dao")
@EnableAutoConfiguration
@EnableCaching
public class OnetomanybidirectionaldemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnetomanybidirectionaldemoApplication.class, args);
    }
}
