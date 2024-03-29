//package com.Homes2Rent.Homes2Rent.controller;
//
//
//import com.Homes2Rent.Homes2Rent.dto.BookingDto;
//import com.Homes2Rent.Homes2Rent.dto.HomeDto;
//import com.Homes2Rent.Homes2Rent.service.BoekingWoningService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Collection;
//
//@RestController
//public class BoekingWoningController {
//
//
//    private BoekingWoningService boekingWoningService;
//
//
//    public BoekingWoningController(BoekingWoningService boekingWoningService) {
//        this.boekingWoningService = boekingWoningService;
//    }
//
//    @PostMapping("/{boekingId}/{woningId}")
//    public void addBoekingWoning(@PathVariable("boekingId") Long boekingId, @PathVariable("woningId") Long woningId) {
//        boekingWoningService.addBoekingWoning(boekingId, woningId);
//    }
//
//    @GetMapping("/boekingwoningen")
//    public ResponseEntity<Object> getall() {
//        return ResponseEntity.ok(boekingWoningService.getAllBoekingenWoningen());
//
//    }
//
//    @GetMapping("/boekingwoningen/{boekingwoningenId}")
//    public Collection<HomeDto> getBoekingWoningByBoekingId(@PathVariable("woningId") Long woningId) {
//        return boekingWoningService.getBoekingWoningByBoekingId(woningId);
//    }
//
//    @GetMapping("/boekingwoningen/{boekingwoningenId}")
//    public Collection<BookingDto> getBoekingWoningByWoningId(@PathVariable("woningId") Long boekingId) {
//        return boekingWoningService.getBoekingWoningByWoningId(boekingId);
//    }
//}
