package com.margobank.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	@Bean
	public RouteLocator margobankRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes().
				route(p -> p
						.path("/margobank/transaction/**")
						.filters(f -> f.rewritePath("/margobank/transaction/?(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								.circuitBreaker(config -> config.setName("transactionCircuitBreaker").setFallbackUri("forward:/contactSupport"))
						)
						.uri("lb://TRANSACTION")
				)
				.route(p -> p
				.path("/margobank/batch/**")
				.filters(f -> f.rewritePath("/margobank/batch/?(?<segment>.*)","/${segment}")
						.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
				)
				.uri("lb://BATCH"))
				.build();
	}

}
