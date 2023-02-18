package com.Homes2Rent.Homes2Rent.controller;

import com.Homes2Rent.Homes2Rent.dto.BoekingDto;
import com.Homes2Rent.Homes2Rent.dto.WoningDto;
import com.Homes2Rent.Homes2Rent.service.BoekingService;
import com.Homes2Rent.Homes2Rent.service.WoningService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
public class WoningController {

    private final WoningService woningService;

    private final BoekingService boekingService;

    public WoningController(WoningService woningService, BoekingService boekingService1) {
        this.woningService = woningService;
        this.boekingService = boekingService1;
    }

    @GetMapping("/woningen")
    public List<WoningDto> getAllWoningen() {

        List<WoningDto> woningen = woningService.getAllWoningen();

        return woningen;
    }

    @GetMapping("/woningen/{id}")
    public WoningDto getWoning(@PathVariable("id") Long id) {

        WoningDto woningDto = woningService.getWoning(id);

        return woningDto;
    }

    @PostMapping("/woningen")
    public WoningDto addWoning(@RequestBody WoningDto woningDto) {

        WoningDto dto1 = woningService.addWoning(woningDto);

        return dto1;
    }

    @DeleteMapping("/woningen/{id}")
    public void deleteWoning(@PathVariable("id") Long id) {
        woningService.deleteWoning(id);
    }

    @PutMapping("/woningen/{id}")
    public WoningDto updateWoning(@PathVariable("id") Long id, @RequestBody WoningDto dto) {
        woningService.updateWoning(id, dto);
        return dto;
    }

//    @GetMapping("/woningen/boekingen/{woningId}")
//    public BoekingDto getBoekingByWoningId(@PathVariable("woningId") Long woningId){
//        return boekingService.getBoekingById(woningId);
//    }
}



