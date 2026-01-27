package com.nagarro.nagp.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Predicate;
@RefreshScope
@Component
public class JwtAuthenticationFilter implements GatewayFilter {

	@Autowired
	private JwtHelper jwtHelper;
	private Logger logger = LoggerFactory.getLogger(GatewayFilter.class);
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();

		final List<String> apiEndpoints = List.of("/api/auth");

		Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream()
				.noneMatch(uri -> r.getURI().getPath().contains(uri));

		if (isApiSecured.test(request)) {
			if (!request.getHeaders().containsKey("Authorization")) {
				ServerHttpResponse response = exchange.getResponse();
				response.setStatusCode(HttpStatus.UNAUTHORIZED);

				return response.setComplete();
			}
			String requestHeader = request.getHeaders().getOrEmpty("Authorization").get(0);
			String token=null;
			if ( requestHeader!= null && requestHeader.startsWith("Bearer")) {
				token = requestHeader.substring(7);
				try {

					if(Boolean.FALSE.equals(jwtHelper.validateToken(token))){
						logger.info("Expired Auth Token");
						return this.onError(exchange, HttpStatus.UNAUTHORIZED);
					}

				} catch (IllegalArgumentException e) {
					logger.info("Illegal Argument while fetching the username !!");
					return this.onError(exchange, HttpStatus.UNAUTHORIZED);
				} catch (ExpiredJwtException e) {
					logger.info("Given jwt token is expired !!");
					return this.onError(exchange, HttpStatus.UNAUTHORIZED);
				} catch (MalformedJwtException e) {
					logger.info("Some changed has done in token !! Invalid Token");
					return this.onError(exchange, HttpStatus.UNAUTHORIZED);
				} catch (Exception e) {
					logger.error(e.getLocalizedMessage());
					return this.onError(exchange, HttpStatus.UNAUTHORIZED);
				}
			} else {
				logger.info("Invalid Header Value !! ");
				return this.onError(exchange, HttpStatus.UNAUTHORIZED);
			}

			}
		return chain.filter(exchange);
	}
	private Mono<Void> onError(ServerWebExchange exchange,HttpStatus httpStatus) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(httpStatus);
		return response.setComplete();
	}
}