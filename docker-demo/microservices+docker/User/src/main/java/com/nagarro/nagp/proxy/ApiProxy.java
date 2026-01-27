package com.nagarro.nagp.proxy;


import com.nagarro.nagp.exception.GenericException;
import com.nagarro.nagp.model.MenuItem;
import com.nagarro.nagp.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class ApiProxy {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${restaurant.endpoint}")
    private String restaurantEndPoint;

    public List<MenuItem> getRestaurantDto(String name) {
        String uri = restaurantEndPoint + name;
        HttpHeaders httpHeaders = new HttpHeaders();
        try {
            ResponseEntity<List<MenuItem>> restaurantDtoResponseEntity = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<String>(httpHeaders), new ParameterizedTypeReference<>() {
            });
            return restaurantDtoResponseEntity.getBody();
        } catch (RuntimeException ex) {
            throw new GenericException("400", ex.getMessage());
        }
    }
}