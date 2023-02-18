package com.Homes2Rent.Homes2Rent.controller;

import com.Homes2Rent.Homes2Rent.dto.BoekingDto;
import com.Homes2Rent.Homes2Rent.dto.BoekingInputDto;
import com.Homes2Rent.Homes2Rent.dto.IdInputDto;
import com.Homes2Rent.Homes2Rent.service.BoekingService;
import com.Homes2Rent.Homes2Rent.service.WoningService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/boekingen")

public class BoekingController {


    private final BoekingService boekingService;

    private final WoningService woningService;

    public BoekingController(BoekingService boekingService, WoningService woningService) {
        this.boekingService = boekingService;
        this.woningService = woningService;
    }

    @GetMapping("/boekingen")
    public ResponseEntity<List<BoekingDto>> getAllBoekingen() {
        List<BoekingDto> dtos = boekingService.getAllBoekingen();
        return ResponseEntity.ok().body(dtos);

    }

    @GetMapping("/boekingen/{id}")
    public ResponseEntity<BoekingDto> getBoekingById(@PathVariable("id") Long id) {

        BoekingDto boeking = boekingService.getBoekingById(id);

        return ResponseEntity.ok().body(boeking);

    }

    @PostMapping("/boekingen")
    public ResponseEntity<Object> addBoeking(@RequestBody BoekingInputDto boekingInputDto) {

        BoekingDto dto = boekingService.addBoeking(boekingInputDto);

        return ResponseEntity.created(null).body(dto);

    }

    @DeleteMapping("/boekingen/{id}")
    public ResponseEntity<Object> deleteBoeking(@PathVariable Long id) {

        boekingService.deleteBoeking(id);

        return ResponseEntity.noContent().build();

    }

    @PutMapping("/boekingen/{id}")
    public ResponseEntity<Object> updateBoeking(@PathVariable Long id, @RequestBody BoekingInputDto newBoeking) {

        BoekingDto dto = boekingService.updateBoeking(id, newBoeking);

        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("/boekingen/{id}/factuur")
    public void assignFactuurToBoeking(@PathVariable("id") Long id, @RequestBody IdInputDto input) {
        boekingService.assignFactuurToBoeking(id, input.id);
    }

    @PutMapping("/boekingen/{id}/{woningId}")
    public void assignWoningToBoeking(@PathVariable("id") Long id, @PathVariable("woningId") Long woningId) {
        boekingService.assignWoningToBoeking(id, woningId);
    }

    @PutMapping("/boekingen/{id}/{annuleringId}")
    public void assignAnnuleringToBoeking(@PathVariable("id") Long id, @PathVariable("annuleringId") Long annuleringId) {
        boekingService.assignAnnuleringToBoeking(id, annuleringId);
    }
}





