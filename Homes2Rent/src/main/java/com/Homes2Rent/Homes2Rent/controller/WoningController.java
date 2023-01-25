package com.Homes2Rent.Homes2Rent.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class WoningController {



    @GetMapping("/woningen")
    public ResponseEntity<Object> getAllWoningen() {

        // Return een String met een 200 status
        return ResponseEntity.ok("woningen");

    }

    @GetMapping("/woningen/{id}")
    public ResponseEntity<Object> getWoning(@PathVariable("id") Long id) {

        // return een String met een 200 status
        return ResponseEntity.ok("woning with id: " + id);

    }

    @PostMapping("/woningen")
    public ResponseEntity<Object> addWoning(@RequestBody String woning) {

        // Return een String met een 201 status
        //De null van created zal over een paar weken vervangen worden door een gegenereerde url.
        return ResponseEntity.created(null).body("woning");

    }

    @DeleteMapping("/woningen/{id}")
    public ResponseEntity<Object> deleteWoning(@PathVariable Long id) {

        //Return een 204 status
        return ResponseEntity.noContent().build();

    }

    @PutMapping("/woningen/{id}")
    public ResponseEntity<Object> updateWoning(@PathVariable Long id, @RequestBody String woning) {

        // Return een 204 status
        return ResponseEntity.noContent().build();

    }


}


