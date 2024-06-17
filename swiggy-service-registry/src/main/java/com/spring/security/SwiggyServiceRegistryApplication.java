package com.spring.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer	// this will enable the eureka server on port 8761
public class SwiggyServiceRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwiggyServiceRegistryApplication.class, args);
	}

}
