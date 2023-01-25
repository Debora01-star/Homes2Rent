package com.Homes2Rent.Homes2Rent.controller;


import com.Homes2Rent.Homes2Rent.dto.FactuurDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Homes2Rent.Homes2Rent.service.FactuurService;

import java.util.List;

@RestController

public class FactuurController {

    private final FactuurService factuurService;;

    public FactuurController(FactuurService factuurService) {
        this.factuurService = factuurService;
    }


    @GetMapping("/facturen")
    public ResponseEntity<Object> getAllFacturen() {

        // Return een String met een 200 status
        return ResponseEntity.ok("facturen");

    }

    @GetMapping("/facturen/{id}")
    public ResponseEntity<Object> getFactuur(@PathVariable("id") Long id) {

        // return een String met een 200 status
        return ResponseEntity.ok("factuur with id: " + id);

    }

    @PostMapping("/facturen")
    public ResponseEntity<Object> addFactuur(@RequestBody String factuur) {

        // Return een String met een 201 status
        //De null van created zal over een paar weken vervangen worden door een gegenereerde url.
        return ResponseEntity.created(null).body("factuur");

    }

    @DeleteMapping("/facturen/{id}")
    public ResponseEntity<Object> deleteFactuur(@PathVariable Long id) {

        //Return een 204 status
        return ResponseEntity.noContent().build();

    }

    @PutMapping("/facturen/{id}")
    public ResponseEntity<Object> updateFactuur(@PathVariable int id, @RequestBody String factuur) {

        // Return een 204 status
        return ResponseEntity.noContent().build();

    }


}


