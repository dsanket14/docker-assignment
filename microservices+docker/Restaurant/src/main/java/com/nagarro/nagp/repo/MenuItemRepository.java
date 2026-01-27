package com.nagarro.nagp.repo;

import com.nagarro.nagp.model.MenuItem;
import com.nagarro.nagp.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findAllByRestaurantName(String restaurantName);

    List<MenuItem> findAllByFoodName(String foodName);

    List<MenuItem> findAllByRestaurantNameAndFoodName(String restaurantName, String foodName);
}

