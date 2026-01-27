package com.nagarro.nagp.service;

import com.nagarro.nagp.exception.GenericException;
import com.nagarro.nagp.model.Restaurant;
import com.nagarro.nagp.repo.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new GenericException("400","Restaurant not found"));
    }

    public Restaurant createRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public Restaurant updateRestaurant(Long id, Restaurant updatedRestaurant) {
        if (!restaurantRepository.existsById(id)) {
            throw new GenericException("400","Restaurant not found");
        }
        Restaurant restaurant=restaurantRepository.findById(id).get();
        restaurant.setAddress(null==updatedRestaurant.getAddress() ? restaurant.getAddress():updatedRestaurant.getAddress());
        restaurant.setName(null==updatedRestaurant.getName() ?restaurant.getName():updatedRestaurant.getName());
        restaurant.setMailId(null==updatedRestaurant.getMailId() ? restaurant.getMailId() : updatedRestaurant.getMailId());
        restaurant.setOperatingHours(null==updatedRestaurant.getOperatingHours()? restaurant.getOperatingHours(): updatedRestaurant.getOperatingHours());
        restaurant.setMenuList(null==updatedRestaurant.getMenuList() ? restaurant.getMenuList(): updatedRestaurant.getMenuList());

        return restaurantRepository.save(restaurant);
    }

    public String deleteRestaurant(Long id) {
        if (!restaurantRepository.existsById(id)) {
            throw new GenericException("400","Restaurant not found");
        }
        restaurantRepository.deleteById(id);
        return "Deleted SuccessFully";
    }
}