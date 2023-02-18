//package com.Homes2Rent.Homes2Rent.service;
//
//
//import com.Homes2Rent.Homes2Rent.dto.BoekingDto;
//import com.Homes2Rent.Homes2Rent.dto.WoningDto;
//import com.Homes2Rent.Homes2Rent.exceptions.RecordNotFoundException;
//import com.Homes2Rent.Homes2Rent.model.Boeking;
//import com.Homes2Rent.Homes2Rent.model.BoekingWoning;
//import com.Homes2Rent.Homes2Rent.model.BoekingWoningKey;
//import com.Homes2Rent.Homes2Rent.model.Woning;
//import com.Homes2Rent.Homes2Rent.repository.BoekingRepository;
//import com.Homes2Rent.Homes2Rent.repository.BoekingWoningRepository;
//import com.Homes2Rent.Homes2Rent.repository.WoningRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.Collection;
//import java.util.HashSet;
//import java.util.List;
//@Service
//public class BoekingWoningService {
//
//    private final BoekingRepository boekingRepository;
//
//    private final WoningRepository woningRepository;
//
//    private final BoekingWoningRepository boekingWoningRepository;
//
//    public BoekingWoningService(BoekingRepository boekingRepository, WoningRepository woningRepository, BoekingWoningRepository boekingWoningRepository) {
//        this.boekingRepository = boekingRepository;
//        this.woningRepository = woningRepository;
//        this.boekingWoningRepository = boekingWoningRepository;
//    }
//
//    public Collection<BoekingDto> getBoekingWoningByWoningId(Long woningId) {
//        Collection<BoekingDto> dtos = new HashSet<>();
//        Collection<BoekingWoning> boekingWoningen = boekingWoningRepository.findAllByWoningId((woningId));
//        for (BoekingWoning boekingWoning : boekingWoningen) {
//            Boeking boeking = boekingWoning.getBoeking();
//            BoekingDto dto = new BoekingDto();
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
//    public Collection<WoningDto> getBoekingWoningByBoekingId(Long boekingId) {
//        Collection<WoningDto> dtos = new HashSet<>();
//        Collection<BoekingWoning> boekingWoningen = boekingWoningRepository.findAllByBoekingId((boekingId));
//        for (BoekingWoning boekingWoning : boekingWoningen) {
//            Woning woning = boekingWoning.getWoning();
//            var dto = new WoningDto();
//
//            dto.setId(woning.getId());
//            dto.setName(woning.getName());
//            dto.setPrice(woning.getPrice());
//            dto.setType(woning.getType());
//            dto.setRented(woning.getRented());
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
//        Boeking boeking = boekingRepository.findById(boekingId).orElse(null);
//        if (!woningRepository.existsById(woningId)) {throw new RecordNotFoundException();}
//        Woning woning = woningRepository.findById(woningId).orElse(null);
//        boekingWoning.setBoeking(boeking);
//        boekingWoning.setWoning(woning);
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
