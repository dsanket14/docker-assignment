package com.nagarro.nagp.proxy;


import com.nagarro.nagp.exception.GenericException;
import com.nagarro.nagp.model.OrderDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class ApiProxy {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${order.endpoint}")
    private String orderEndPoint;
    @CircuitBreaker(name="payment", fallbackMethod = "paymentFallback")
    public OrderDto getOrder(Long id) {
        String uri = orderEndPoint + id;
        HttpHeaders httpHeaders = new HttpHeaders();
        try {
            ResponseEntity<OrderDto> restaurantDtoResponseEntity = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<String>(httpHeaders), new ParameterizedTypeReference<>() {
            });
            return restaurantDtoResponseEntity.getBody();
        } catch (RuntimeException ex) {
            throw new RuntimeException("Issue with payment sever. please try COD");
        }
    }

    public void paymentFallback(Throwable throwable){
        throw new RuntimeException("PAYMENT_SERVICE: Issue with payment sever. please try COD");

    }
}