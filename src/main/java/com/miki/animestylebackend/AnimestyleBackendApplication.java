package com.miki.animestylebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
@SpringBootApplication
@EnableAsync
@EnableCaching
@EnableJpaRepositories(basePackages = "com.miki.animestylebackend.repository.jpa")
@EnableRedisRepositories(basePackages = "com.miki.animestylebackend.repository.redis")
public class AnimestyleBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnimestyleBackendApplication.class, args);
	}

}
