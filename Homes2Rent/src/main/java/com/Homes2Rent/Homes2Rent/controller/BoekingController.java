package com.Homes2Rent.Homes2Rent.controller;
import ch.qos.logback.core.joran.action.Action;
import com.Homes2Rent.Homes2Rent.dto.BoekingDto;
import com.Homes2Rent.Homes2Rent.dto.BoekingInputDto;
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

    @GetMapping( "")
    public ResponseEntity<List<BoekingDto>> getAllBoekingen() {
        List<BoekingDto> dtos = boekingService.getAllBoekingen();
        return ResponseEntity.ok().body(dtos);

    }

    @GetMapping("{id}")
    public ResponseEntity<BoekingDto> getBoekingById(@PathVariable("id") Long id) {

        BoekingDto boeking = boekingService.getBoekingById(id);

        return ResponseEntity.ok().body(boeking);

    }

    @PostMapping("")
    public ResponseEntity<Object> addBoeking(@RequestBody BoekingInputDto boekingInputDto) {

        BoekingDto dto = boekingService.addBoeking(boekingInputDto);

        return ResponseEntity.created(null).body(dto);

    }


    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteBoeking(@PathVariable Long id) {

        boekingService.deleteBoeking(id);

        return ResponseEntity.noContent().build();

    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateBoeking(@PathVariable Long id, @RequestBody BoekingInputDto newBoeking) {

        BoekingDto dto = boekingService.updateBoeking(id, newBoeking);

        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("{id}/factuur/{factuur_id}")
    public void assignFactuurToBoeking(@PathVariable("id") Long id, @PathVariable ("factuur_id") Long factuur_id) {
        boekingService.assignFactuurToBoeking(id, factuur_id);
    }

    @PutMapping("/{id}/{woningId}")
    public void assignWoningToBoeking(@PathVariable("id") Long id, @PathVariable("woningId") Long woningId) {
        boekingService.assignWoningToBoeking(id, woningId);
    }

    @PutMapping("/boekingen/{id}/{annuleringId}")
    public void assignAnnuleringToBoeking(@PathVariable("id") Long id, @PathVariable("annuleringId") Long annuleringId) {
        boekingService.assignAnnuleringToBoeking(id, annuleringId);
    }
}





