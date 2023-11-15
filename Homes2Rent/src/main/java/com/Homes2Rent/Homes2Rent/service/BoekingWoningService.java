//package com.Homes2Rent.Homes2Rent.service;
//
//
//import com.Homes2Rent.Homes2Rent.dto.BookingDto;
//import com.Homes2Rent.Homes2Rent.dto.HomeDto;
//import com.Homes2Rent.Homes2Rent.exceptions.RecordNotFoundException;
//import com.Homes2Rent.Homes2Rent.model.Booking;
//import com.Homes2Rent.Homes2Rent.model.BoekingWoning;
//import com.Homes2Rent.Homes2Rent.model.BoekingWoningKey;
//import com.Homes2Rent.Homes2Rent.model.Home;
//import com.Homes2Rent.Homes2Rent.repository.BookingRepository;
//import com.Homes2Rent.Homes2Rent.repository.BoekingWoningRepository;
//import com.Homes2Rent.Homes2Rent.repository.HomeRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.Collection;
//import java.util.HashSet;
//import java.util.List;
//@Service
//public class BoekingWoningService {
//
//    private final BookingRepository boekingRepository;
//
//    private final HomeRepository woningRepository;
//
//    private final BoekingWoningRepository boekingWoningRepository;
//
//    public BoekingWoningService(BookingRepository boekingRepository, HomeRepository woningRepository, BoekingWoningRepository boekingWoningRepository) {
//        this.boekingRepository = boekingRepository;
//        this.woningRepository = woningRepository;
//        this.boekingWoningRepository = boekingWoningRepository;
//    }
//
//    public Collection<BookingDto> getBoekingWoningByWoningId(Long woningId) {
//        Collection<BookingDto> dtos = new HashSet<>();
//        Collection<BoekingWoning> boekingWoningen = boekingWoningRepository.findAllByWoningId((woningId));
//        for (BoekingWoning boekingWoning : boekingWoningen) {
//            Booking boeking = boekingWoning.getBoeking();
//            BookingDto dto = new BookingDto();
//
//            boeking.setId(dto.getId());
//            boeking.setType_boeking(dto.getType_boeking());
//            boeking.setFinish_date(dto.getFinish_date());
//            boeking.setStatus(dto.getStatus());
//            boeking.setPrice(dto.getPrice());
//            boeking.setWoning(dto.getWoning());
//
//            dtos.add(dto);
//        }
//        return dtos;
//    }
//    public Collection<HomeDto> getBoekingWoningByBoekingId(Long boekingId) {
//        Collection<HomeDto> dtos = new HashSet<>();
//        Collection<BoekingWoning> boekingWoningen = boekingWoningRepository.findAllByBoekingId((boekingId));
//        for (BoekingWoning boekingWoning : boekingWoningen) {
//            Home home = boekingWoning.getWoning();
//            var dto = new HomeDto();
//
//            dto.setId(home.getId());
//            dto.setName(home.getName());
//            dto.setPrice(home.getPrice());
//            dto.setType(home.getType());
//            dto.setRented(home.getRented());
//
//            dtos.add(dto);
//        }
//        return dtos;
//    }
//
//    public  BoekingWoningKey addBoekingWoning(Long boekingId, Long woningId) {
//
//        var boekingWoning = new BoekingWoning();
//
//        if (!boekingRepository.existsById(boekingId)) {throw new RecordNotFoundException();}
//        Booking boeking = boekingRepository.findById(boekingId).orElse(null);
//        if (!woningRepository.existsById(woningId)) {throw new RecordNotFoundException();}
//        Home home = woningRepository.findById(woningId).orElse(null);
//        boekingWoning.setBoeking(boeking);
//        boekingWoning.setWoning(home);
//        BoekingWoningKey id = new BoekingWoningKey(boekingId, woningId);
//        boekingWoning.setId(id);
//        boekingWoningRepository.save(boekingWoning);
//
//        return id;
//    }
//    public List<BoekingWoning> getAllBoekingenWoningen(){
//        return boekingWoningRepository.findAll();
//    }
//
//    }
//
//
