package com.Homes2Rent.Homes2Rent.controller;

import com.Homes2Rent.Homes2Rent.dto.AnnuleringDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Homes2Rent.Homes2Rent.service.AnnuleringsService;

import java.util.List;

@RestController

public class AnnuleringController {

    private final AnnuleringsService annuleringsService;

    public AnnuleringController(AnnuleringsService annuleringsService) {
        this.annuleringsService = annuleringsService;
    }

    @GetMapping("/annuleringen")
    public ResponseEntity<Object> getAllAnnuleringen() {

        // Return een String met een 200 status
        return ResponseEntity.ok("annulering");

    }

    @GetMapping("/annuleringen/{id}")
    public ResponseEntity<Object> getAnnulering(@PathVariable("id") Long id) {

        // return een String met een 200 status
        return ResponseEntity.ok("annulering with id: " + id);

    }

    @PostMapping("/annuleringen")
    public ResponseEntity<Object> addAnnulering(@RequestBody String annulering) {

        // Return een String met een 201 status
        //De null van created zal over een paar weken vervangen worden door een gegenereerde url.
        return ResponseEntity.created(null).body("annulering");

    }

    @DeleteMapping("/annuleringen/{id}")
    public ResponseEntity<Object> deleteAnnulering(@PathVariable Long id) {

        //Return een 204 status
        return ResponseEntity.noContent().build();

    }

    @PutMapping("/annuleringen/{id}")
    public ResponseEntity<Object> updateAnnulering(@PathVariable Long id, @RequestBody String annulering) {

        // Return een 204 status
        return ResponseEntity.noContent().build();

    }


}

