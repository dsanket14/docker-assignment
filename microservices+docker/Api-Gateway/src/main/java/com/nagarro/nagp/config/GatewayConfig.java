package com.nagarro.nagp.config;


import com.nagarro.nagp.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Autowired
    JwtAuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r.path("/api/users/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://USER-SERVICE"))
                .route("user-service", r -> r.path("/api/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://user-service"))
                .route("restaurant-service", r -> r.path("/api/restaurants/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://restaurant-service"))
                .route("order-service", r -> r.path("/api/orders/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://order-service"))
                .route("order-service", r -> r.path("/api/dining/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://order-service"))
                .route("payment-service", r -> r.path("/api/payment/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://payment-service"))
                .build();
    }

}