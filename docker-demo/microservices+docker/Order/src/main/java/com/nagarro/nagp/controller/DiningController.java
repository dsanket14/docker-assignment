package com.nagarro.nagp.controller;

import com.nagarro.nagp.model.Dining;
import com.nagarro.nagp.service.DiningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/dining")
public class DiningController {

    @Autowired
    private DiningService diningService;

    @PostMapping
    public ResponseEntity<Dining> createDiningOrder(@RequestBody Dining dining) {
        Dining createdDining = diningService.createDiningOrder(dining);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDining);
    }

    @GetMapping
    public List<Dining> getAllDiningOrder() {
        return diningService.getAllDining();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dining> getDiningById(@PathVariable Long id) {
        Dining Dining = diningService.getDiningById(id);
        return ResponseEntity.ok(Dining);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Dining> updateDiningStatus(@PathVariable Long id, @RequestParam String status) {
        Dining updatedDining = diningService.updateDiningStatus(id, status);
        return ResponseEntity.ok(updatedDining);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDining(@PathVariable Long id) {
        return ResponseEntity.ok(diningService.deleteDiningOrder(id));
    }
}

