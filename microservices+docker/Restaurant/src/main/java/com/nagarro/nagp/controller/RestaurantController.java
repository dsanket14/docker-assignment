package com.nagarro.nagp.controller;

import com.nagarro.nagp.model.MenuItem;
import com.nagarro.nagp.model.Restaurant;
import com.nagarro.nagp.service.MenuItemService;
import com.nagarro.nagp.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private MenuItemService menuItemService;

    @GetMapping
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        return ResponseEntity.ok(restaurant);
    }

    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
        Restaurant createdRestaurant = restaurantService.createRestaurant(restaurant);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRestaurant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Long id, @RequestBody Restaurant restaurant) {
        Restaurant updatedRestaurant = restaurantService.updateRestaurant(id, restaurant);
        return ResponseEntity.ok(updatedRestaurant);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantService.deleteRestaurant(id));
    }

    @GetMapping("/foodname/{foodName}")
    public ResponseEntity<List<MenuItem>> getByFoodName(@PathVariable String foodName){
        return ResponseEntity.ok(menuItemService.getByFoodName(foodName));
    }
    @GetMapping("/name/{restaurantName}")
    public ResponseEntity<List<MenuItem>> getByRestaurantName(@PathVariable String restaurantName){
        return ResponseEntity.ok(menuItemService.getByRestaurantName(restaurantName));
    }

    @GetMapping("/name-food/{restaurantName}/{foodName}")
    public ResponseEntity<List<MenuItem>> getByRestaurantName(@PathVariable String restaurantName,@PathVariable String foodName){
        return ResponseEntity.ok(menuItemService.getByRestaurantNameAndFoodName(restaurantName,foodName));
    }
}
