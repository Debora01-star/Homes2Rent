package com.Homes2Rent.Homes2Rent.controller;

import com.Homes2Rent.Homes2Rent.service.BoekingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/boekingen")

public class BoekingController {


        private final BoekingService boekingService;


        public BoekingController(BoekingService boekingService) {
            this.boekingService = boekingService;
        }

    @GetMapping("/boekingen")
    public ResponseEntity<Object> getAllBoekingen() {

        // Return een String met een 200 status
        return ResponseEntity.ok("boeking");

    }

    @GetMapping("/boeking/{id}")
    public ResponseEntity<Object> getBoeking(@PathVariable("id") Long id) {

        // return een String met een 200 status
        return ResponseEntity.ok("boeking with id: " + id);

    }

    @PostMapping("/boekingen")
    public ResponseEntity<Object> addBoeking(@RequestBody String boeking) {

        // Return een String met een 201 status
        //De null van created zal over een paar weken vervangen worden door een gegenereerde url.
        return ResponseEntity.created(null).body("boeking");

    }

    @DeleteMapping("/boekingen/{id}")
    public ResponseEntity<Object> deleteBoekingen(@PathVariable Long id) {

        //Return een 204 status
        return ResponseEntity.noContent().build();

    }

    @PutMapping("/boekingen/{id}")
    public ResponseEntity<Object> updateBoeking(@PathVariable Long id, @RequestBody String boeking) {

        // Return een 204 status
        return ResponseEntity.noContent().build();

    }


}

