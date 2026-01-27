package com.nagarro.nagp.service;

import com.nagarro.nagp.exception.GenericException;
import com.nagarro.nagp.model.MenuItem;
import com.nagarro.nagp.model.Restaurant;
import com.nagarro.nagp.model.User;
import com.nagarro.nagp.model.UserFavourite;
import com.nagarro.nagp.proxy.ApiProxy;
import com.nagarro.nagp.repo.UserFavouriteRepo;
import com.nagarro.nagp.repo.UserRepo;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    UserFavouriteRepo userFavouriteRepo;

    @Autowired
    ApiProxy apiProxy;


    @PostConstruct
    public void init() {
        User user=new User(1L,"sanket","sanket123","7798892075","dsanket188@gmail.com");
        userRepo.save(user);
        log.info("User Initialized for JWT Token Creation");
    }

    public User registerUser(User user) {
        Optional<User> existingUser = userRepo.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            throw new GenericException("400","Username already exists");
        }
        log.info("{} : user Registered Successfully ", user.getUsername());
        return userRepo.save(user);
    }

    public User getUser(String userName) {
        Optional<User> existingUser = userRepo.findByUsername(userName);
        if (existingUser.isEmpty()) {
            throw new GenericException("400","Username Not Found");
        }
        return existingUser.get();
    }

    public User getUserById(Long id) {
        Optional<User> existingUser = userRepo.findById(id);
        if (existingUser.isEmpty()) {
            throw new GenericException("400","Username Not Found");
        }
        existingUser.get().setPassword("********");
        return existingUser.get();
    }

    public String deleteUser(Long id){
        Optional<User> existingUser = userRepo.findById(id);
        if (existingUser.isEmpty()) {
            throw new GenericException("400","Username Not Found");
        }
        userRepo.deleteById(id);
        log.info("user Deleted SuccessFully");
        return "User Deleted Successfully";
    }

    public List<UserFavourite> getMyfavouriteRestroList(){
        return userFavouriteRepo.findAll();
    }

    public String addToFavourite(String username ,String restaurantName){
        List<MenuItem> menuItem= apiProxy.getRestaurantDto(restaurantName);
        if(!menuItem.isEmpty()) {
            UserFavourite userfavourite = new UserFavourite();
            userfavourite.setUsername(username);
            userfavourite.setRestaurantName(menuItem.get(0).getRestaurantName());
            userFavouriteRepo.save(userfavourite);
            log.info("{} : added to favourite List ", restaurantName);
            return restaurantName + " added to favourite List";
        }
        throw new GenericException("400","Restaurant Not Found");
    }

    public String deleteFavouriteRestaurant(String id) {
        Optional<UserFavourite> existingUser = userFavouriteRepo.findById(Long.valueOf(id));
        if (existingUser.isEmpty()) {
            throw new GenericException("400","favourite Restaurant Not Found");
        }
        userFavouriteRepo.deleteById(Long.valueOf(id));
        return "Favourite Restaurant Deleted Successfully";
    }
}
