package com.Homes2Rent.Homes2Rent.controller;


import com.Homes2Rent.Homes2Rent.dto.FactuurDto;
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
    public List<FactuurDto> getAllFactuur() {

        List<FactuurDto> dtos = factuurService.getAllFacturen();

        return dtos;
    }


    @GetMapping("/facturen/{id}")
    public FactuurDto getFactuur(@PathVariable("id") Long id) {

        FactuurDto dto = factuurService.getFactuur(id);

        return dto;
    }

    @PostMapping("/facturen")
    public FactuurDto addFactuur(@RequestBody FactuurDto dto) {
        FactuurDto dto1 = factuurService.addFactuur(dto);
        return dto1;
    }

    @DeleteMapping("/facturen/{id}")
    public void deleteFactuur(@PathVariable("id") Long id) {
        factuurService.deleteFactuur(id);
    }

    @PutMapping("/facturen/{id}")
    public FactuurDto updateFactuur(@PathVariable("id") Long id, @RequestBody FactuurDto dto) {
        factuurService.updateFactuur(id, dto);
        return dto;
    }

}



