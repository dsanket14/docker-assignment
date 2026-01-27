package com.nagarro.nagp.proxy;


import com.nagarro.nagp.dto.RestaurantDto;
import com.nagarro.nagp.dto.UserDto;
import com.nagarro.nagp.exception.GenericException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ApiProxy {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${restaurant.endpoint}")
    private String restaurantEndPoint;

    @Value("${user.endpoint}")
    private String userEndPoint;
    public RestaurantDto getRestaurantDto(Long id) {
        String uri = restaurantEndPoint +id;
        HttpHeaders httpHeaders = new HttpHeaders();
        try {
            ResponseEntity<RestaurantDto> restaurantDtoResponseEntity = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<String>(httpHeaders), new ParameterizedTypeReference<>() {
            });
            return restaurantDtoResponseEntity.getBody();
        } catch (RuntimeException ex) {
            throw new GenericException("400", ex.getMessage());
        }
    }

    public UserDto getUserDto(Long id) {
        String uri = userEndPoint +id;
        HttpHeaders httpHeaders = new HttpHeaders();
        try {
            ResponseEntity<UserDto> userDtoResponseDto = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<String>(httpHeaders), new ParameterizedTypeReference<>() {
            });
            return userDtoResponseDto.getBody();
        } catch (RuntimeException ex) {
            throw new GenericException("400", ex.getMessage());
        }
    }
}