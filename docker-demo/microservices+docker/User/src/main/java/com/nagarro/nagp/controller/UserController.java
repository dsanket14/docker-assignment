package com.nagarro.nagp.controller;

import com.nagarro.nagp.model.User;
import com.nagarro.nagp.model.UserFavourite;
import com.nagarro.nagp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("user")
    public String helloUser() {
        return "Hello User";
    }

    @GetMapping("admin")
    public String helloAdmin() {
        return "Hello Admin";
    }
    @PostMapping("/register")
    private ResponseEntity<User> saveUser(@RequestBody User user){
        userService.registerUser(user);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/add-favourite")
    public ResponseEntity<String> addToFavouriteList(@RequestParam String userName, @RequestParam String restaurant){
        return ResponseEntity.ok(userService.addToFavourite(userName,restaurant));
    }

    @GetMapping("/get-favourite")
    public ResponseEntity<List<UserFavourite>> getFavouriteList(){
        return ResponseEntity.ok(userService.getMyfavouriteRestroList());
    }

    @DeleteMapping("/rmv-favourite/{id}")
    public ResponseEntity<String> removeFavouriteRestaurant(@PathVariable String id){
        return ResponseEntity.ok(userService.deleteFavouriteRestaurant(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getFavouriteList(@PathVariable String id){
        return ResponseEntity.ok(userService.getUserById(Long.valueOf(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id){
        return ResponseEntity.ok(userService.deleteUser(Long.valueOf(id)));
    }
}
