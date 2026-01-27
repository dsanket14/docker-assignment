package com.nagarro.nagp.repo;

import com.nagarro.nagp.model.Dining;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiningRepo extends JpaRepository<Dining,Long> {
}
