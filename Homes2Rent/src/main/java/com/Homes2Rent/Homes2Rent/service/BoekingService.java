package com.Homes2Rent.Homes2Rent.service;


import com.Homes2Rent.Homes2Rent.dto.BoekingDto;
import com.Homes2Rent.Homes2Rent.dto.BoekingInputDto;
import com.Homes2Rent.Homes2Rent.exceptions.RecordNotFoundException;
import com.Homes2Rent.Homes2Rent.model.Boeking;
import com.Homes2Rent.Homes2Rent.repository.BoekingRepository;
import com.Homes2Rent.Homes2Rent.repository.WoningRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoekingService {


    private final BoekingRepository boekingRepository;
    private final WoningRepository woningRepository;

    public BoekingService(BoekingRepository repos, WoningRepository woningRepository) {
        this.boekingRepository = repos;
        this.woningRepository = woningRepository;
    }


    public BoekingDto addBoeking(BoekingInputDto dto) {

    Boeking boeking = transferToBoeking(dto);
            boekingRepository.save(boeking);
            return transferToDto(boeking);

}


        public BoekingDto updateBoeking(BoekingInputDto dto,Long id) {
        if(boekingRepository.findById(id).isPresent()) {
            Boeking boeking = boekingRepository.findById(id).get();

            Boeking updateBoeking = transferToBoeking(dto);
            updateBoeking.setId(boeking.getId());
            boekingRepository.save(updateBoeking);
            return transferToDto(updateBoeking);
        } else {
            throw new RecordNotFoundException("no boeking found");
        }

         }

         public void deleteBoeking(Long id) {
            boekingRepository.deleteById(id);
        }

        public List<BoekingDto> getAllBoekingen() {

        List<BoekingDto> boekingDtoList = new ArrayList<>();
        List<Boeking> boekingList = (List<Boeking>) boekingRepository.findAll();
        for (Boeking boeking : boekingList) {
            BoekingDto dto = transferToDto(boeking);
            boekingDtoList.add(dto);
        }
        return boekingDtoList;
        }

        public BoekingDto getBoeking(Long id) {
        Optional<Boeking> boeking = boekingRepository.findById(id);
        if (boeking.isPresent()) {
            Boeking b = boeking.get();
            return transferToDto(b);
        } else {
            throw new RecordNotFoundException("Boeking not found");
        }
        }

        public BoekingDto transferToDto(Boeking boeking) {
        BoekingDto dto = new BoekingDto();
        dto.setId(boeking.getId());
        dto.setType_boeking(boeking.getType_boeking());
        dto.setFinish_date(boeking.getFinish_date());
        dto.setNotes(boeking.getNotes());
        dto.setStatus(boeking.getStatus());
        dto.setWoning(boeking.getWoning());
        dto.setPrice(boeking.getPrice());
        return dto;
        }

        public Boeking transferToBoeking(BoekingInputDto dto) {

            Boeking boeking = new Boeking();
            boeking.setType_boeking(dto.getType_boeking());
            boeking.setFinish_date(dto.getFinish_date());
            boeking.setNotes(dto.getNotes());
            boeking.setStatus(dto.getStatus());
            boeking.setWoning(dto.getWoning());
            boeking.setPrice(dto.getPrice());
            boeking.setPrice(dto.getPrice());
            return boeking;


    }
}

