package com.nagarro.nagp.repo;

import com.nagarro.nagp.model.User;
import com.nagarro.nagp.model.UserFavourite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserFavouriteRepo extends JpaRepository<UserFavourite,Long> {
}
