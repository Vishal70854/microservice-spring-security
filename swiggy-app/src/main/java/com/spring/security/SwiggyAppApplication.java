package com.spring.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient    // this annotation will register the swiggy-app in eureka server registry
public class SwiggyAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwiggyAppApplication.class, args);
	}

}
