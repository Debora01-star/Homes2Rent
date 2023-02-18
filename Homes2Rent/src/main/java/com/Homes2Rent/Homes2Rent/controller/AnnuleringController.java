package com.Homes2Rent.Homes2Rent.controller;

import com.Homes2Rent.Homes2Rent.dto.AnnuleringDto;
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
    public List<AnnuleringDto> getAllAnnuleringen() {

        var dtos = annuleringsService.getAllAnnuleringen();

        return dtos;
    }

    @GetMapping("/annuleringen/{id}")
    public AnnuleringDto getAnnulering(@PathVariable("id") Long id) {

        AnnuleringDto annuleringDto = annuleringsService.getAnnulering(id);

        return annuleringDto;
    }

    @PostMapping("/annuleringen")
    public AnnuleringDto addAnnulering(@RequestBody AnnuleringDto dto) {
        AnnuleringDto annuleringDto = annuleringsService.addAnnulering(dto);
        return annuleringDto;
    }

    @DeleteMapping("/annuleringen/{id}")
    public void deleteAnnulering(@PathVariable("id") Long id) {
        annuleringsService.deleteAnnulering(id);

    }

    @PutMapping("/annuleringen/{id}")
    public AnnuleringDto updateAnnulering(@PathVariable("id") Long id, @RequestBody AnnuleringDto annuleringDto) {
        annuleringsService.updateAnnulering(id, annuleringDto);
        return annuleringDto;
    }


}

