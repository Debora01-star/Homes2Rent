package com.Homes2Rent.Homes2Rent.service;


import com.Homes2Rent.Homes2Rent.dto.BoekingDto;
import com.Homes2Rent.Homes2Rent.dto.BoekingInputDto;
import com.Homes2Rent.Homes2Rent.exceptions.RecordNotFoundException;
import com.Homes2Rent.Homes2Rent.model.Boeking;
import com.Homes2Rent.Homes2Rent.model.Factuur;
import com.Homes2Rent.Homes2Rent.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoekingService {


    private final BoekingRepository boekingRepository;

    private final FactuurRepository factuurRepository;

    private final FactuurService factuurService;

    private final WoningRepository woningRepository;

    private final WoningService woningService;

    private final AnnuleringRepository annuleringRepository;
    private final AnnuleringsService annuleringsService;



    public BoekingService(BoekingRepository boekingRepository, FactuurRepository factuurRepository, FactuurService factuurService, WoningRepository woningRepository, WoningService woningService, AnnuleringRepository annuleringRepository, AnnuleringsService annuleringsService) {
        this.boekingRepository = boekingRepository;
        this.factuurRepository = factuurRepository;
        this.factuurService = factuurService;
        this.woningRepository = woningRepository;
        this.woningService = woningService;
        this.annuleringRepository = annuleringRepository;
        this.annuleringsService = annuleringsService;
    }


    public List<BoekingDto> getAllBoekingen() {
        List<Boeking> boekingList = boekingRepository.findAll();
        return transferBoekingListToDtoList(boekingList);
    }


//    public List<BoekingDto> getAllBoekingenByReservation(String reservation) {
//        List<Boeking> boekingList = boekingRepository.findAllBoekingenByReservationEqualsIgnoreCase(reservation);
//        return transferBoekingListToDtoList(boekingList);
//    }
    public List<BoekingDto> transferBoekingListToDtoList(List<Boeking> boekingen){
        List<BoekingDto> boekingDtoList = new ArrayList<>();

        for(Boeking boeking : boekingen) {
            BoekingDto dto = transferToDto(boeking);
            if(boeking.getAnnulering() != null){
                dto.setAnnuleringDto(annuleringsService.transferToDto(boeking.getAnnulering()));
            }
            if(boeking.getFactuur() != null){
                dto.setFactuurDto(factuurService.transferToDto(new Factuur().getFactuur()));
            }
            boekingDtoList.add(dto);
        }
        return boekingDtoList;
    }

    public BoekingDto getBoekingById(Long id) {

        if (boekingRepository.findById(id).isPresent()){
            Boeking boeking = boekingRepository.findById(id).get();
            BoekingDto dto =transferToDto(boeking);
            if(boeking.getAnnulering() != null){
                dto.setAnnuleringDto(annuleringsService.transferToDto(boeking.getAnnulering()));
            }
            if(boeking.getFactuur() != null){
                dto.setFactuurDto(factuurService.transferToDto(new Factuur().getFactuur()));
            }

            return transferToDto(boeking);
        } else {
            throw new RecordNotFoundException("geen boeking gevonden");
        }
    }

    private BoekingDto transferToDto(Boeking boeking) {

        BoekingDto dto = new BoekingDto();

        dto.setId(boeking.getId());
        dto.setType_boeking(boeking.getType_boeking());
        dto.setFinish_date(boeking.getFinish_date());
        dto.setStatus(boeking.getStatus());
        dto.setWoning(boeking.getWoning());
        dto.setPrice(boeking.getPrice());

        return dto;

    }

    public BoekingDto addBoeking(BoekingInputDto dto) {

        Boeking boeking = transferToBoeking(dto);
        boekingRepository.save(boeking);

        return transferToDto(boeking);
    }


    public void deleteBoeking(Long id) {

        boekingRepository.deleteById(id);

    }
    public BoekingDto updateBoeking(Long id, BoekingInputDto inputDto) {

        if(boekingRepository.findById(id).isEmpty()){
            throw new RecordNotFoundException("geen boeking gevonden");
        } else {
            Boeking boeking = boekingRepository.findById(id).get();

            Boeking boeking1 = transferToBoeking(inputDto);
            boeking1.setId(boeking.getId());

            boekingRepository.save(boeking1);

            return transferToDto(boeking1);

        }
    }

    Boeking transferToBoeking(BoekingInputDto inputDto) {

        var boeking = new Boeking();

        boeking.setType_boeking(inputDto.getType_boeking());
        boeking.setFinish_date(inputDto.getFinish_date());
        boeking.setStatus(inputDto.getStatus());
        boeking.setWoning(inputDto.getWoning());
        boeking.setPrice(inputDto.getPrice());
        boeking.setPrice(inputDto.getPrice());

        return boeking;

    }

    public void assignFactuurToBoeking(Long id, Long factuurId) {
            var optionalBoeking = boekingRepository.findById(id);
            var optionalFactuur = factuurRepository.findById(factuurId);

            if(optionalBoeking.isPresent() && optionalFactuur.isPresent()) {
                var boeking = optionalBoeking.get();
                var factuur = optionalFactuur.get();

                boeking.setFactuur(factuur);
                boekingRepository.save(boeking);
            } else {
                throw new RecordNotFoundException();
            }
        }

        public void assignAnnuleringToBoeking(Long id, Long annuleringId) {
            var optionalBoeking = boekingRepository.findById(id);
            var optionalAnnulering = annuleringRepository.findById(annuleringId);

            if(optionalBoeking.isPresent() && optionalAnnulering.isPresent()) {
                var boeking = optionalBoeking.get();
                var annulering = optionalAnnulering.get();

                boeking.setAnnulering(annulering);
                boekingRepository.save(boeking);
            } else {
                throw new RecordNotFoundException();
            }
        }

    public void assignWoningToBoeking(Long id, Long woningId) {

        var optionalBoeking = boekingRepository.findById(id);
        var optionalWoning = woningRepository.findById(woningId);

        if(optionalBoeking.isPresent() && optionalWoning.isPresent()) {
            var boeking = optionalBoeking.get();
            var woning = optionalWoning.get();

            boeking.setWoning(woning);
            boekingRepository.save(boeking);
        } else {
            throw new RecordNotFoundException();
        }
    }

}








