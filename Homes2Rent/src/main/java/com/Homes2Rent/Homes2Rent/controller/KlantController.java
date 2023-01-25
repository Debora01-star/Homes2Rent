package com.Homes2Rent.Homes2Rent.controller;


import com.Homes2Rent.Homes2Rent.dto.KlantDto;
import com.Homes2Rent.Homes2Rent.dto.KlantInputDto;
import com.Homes2Rent.Homes2Rent.exceptions.DuplicatedEntryException;
import com.Homes2Rent.Homes2Rent.exceptions.RecordNotFoundException;
import org.springframework.web.bind.annotation.*;
import com.Homes2Rent.Homes2Rent.service.KlantService;

import java.util.List;

@RestController

public class KlantController {

    private final KlantService klantService;


    public KlantController(KlantService klantService ) {
        this.klantService = klantService;
    }


    @GetMapping("/klanten")
    public List<KlantDto> getAllKlanten() {

        KlantDto klantDto = (KlantDto) klantService.getAllKlanten();

        return (List<KlantDto>) klantDto;
    }

    @GetMapping("/klanten/{id}")
    public KlantDto getKlant(@PathVariable("id") Long id) {

        KlantDto klantDto = klantService.getKlant(id);

        return klantDto;
    }

    @PostMapping("/klanten")
    public KlantDto addKlant(@RequestBody KlantInputDto dto) throws DuplicatedEntryException {
        return klantService.addKlant(dto);
    }

    @DeleteMapping("/klanten/{id}")
    public void deleteKlant(@PathVariable("id") Long id) {
        klantService.deleteKlant(id);
    }

    @PutMapping("/klanten/{id}")
    public KlantDto updateKlant(@PathVariable("id") Long id, @RequestBody KlantInputDto klantDto) throws DuplicatedEntryException, RecordNotFoundException {
        return klantService.updateKlant(id, klantDto);
    }

}




