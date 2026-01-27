package com.nagarro.nagp.service;

import com.nagarro.nagp.exception.GenericException;
import com.nagarro.nagp.model.MenuItem;
import com.nagarro.nagp.model.Restaurant;
import com.nagarro.nagp.repo.MenuItemRepository;
import com.nagarro.nagp.repo.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    public List<MenuItem> getAllRestaurants() {
        return menuItemRepository.findAll();
    }

    public List<MenuItem> getByRestaurantName(String restaurantName) {
        return menuItemRepository.findAllByRestaurantName(restaurantName);
    }

    public List<MenuItem> getByFoodName(String foodName) {
        return menuItemRepository.findAllByFoodName(foodName);
    }
    public List<MenuItem> getByRestaurantNameAndFoodName(String restaurantName,String foodName) {
        return menuItemRepository.findAllByRestaurantNameAndFoodName(restaurantName,foodName);
    }


}