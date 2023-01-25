package com.Homes2Rent.Homes2Rent.service;

import com.Homes2Rent.Homes2Rent.dto.WoningDto;
import com.Homes2Rent.Homes2Rent.dto.WoningInputDto;
import com.Homes2Rent.Homes2Rent.exceptions.DuplicatedEntryException;
import com.Homes2Rent.Homes2Rent.exceptions.RecordNotFoundException;
import com.Homes2Rent.Homes2Rent.model.Woning;
import com.Homes2Rent.Homes2Rent.repository.KlantRepository;
import com.Homes2Rent.Homes2Rent.repository.WoningRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class WoningService {


        private WoningRepository woningRepository;

        private final KlantRepository klantRepository;

        public WoningService(WoningRepository woningRepository, KlantRepository klantRepository)

        {
            this.woningRepository = woningRepository;
            this.klantRepository = klantRepository;

        }
        public WoningDto addWoning(WoningInputDto dto) throws DuplicatedEntryException {
            if (woningRepository.existsById(dto.getId())) {
                throw new DuplicatedEntryException("Woning already exists");
            } else {
                Woning woning = transferToWoning(dto);
                woningRepository.save(woning);
                return transferToDto(woning);
            }

        }

        public WoningDto updateWoning(Long id, WoningInputDto dto) throws DuplicatedEntryException {
            if (woningRepository.findById(id).isPresent()) {
                Woning woning = woningRepository.findById(id).get();

                Woning existingwoning = woningRepository.findWoningById(dto.getId());

                if (existingwoning != null && !existingwoning.getId().equals(woning.getId())) {
                    throw new DuplicatedEntryException("Woning already exists");
                } else {

                    Woning updatewoning = transferToWoning(dto);
                    updatewoning.setId(woning.getId());
                    Woning savedWoning = woningRepository.save(updatewoning);
                    return transferToDto(updatewoning);
                }
            } else {
                throw new RecordNotFoundException("no woning found");
            }

        }
        public void deleteWoning(Long id) {
        woningRepository.deleteById(id);
    }

        public Collection<WoningDto> getAllWoningen() {

        List<WoningDto> woningDtoList = new ArrayList<>();
        List<Woning> woningList = (List<Woning>) woningRepository.findAll();
        for (Woning woning : woningList) {
            WoningDto dto = transferToDto(woning);
            woningDtoList.add(dto);
        }
        return woningDtoList;
    }

        public WoningDto getWoning(Long id) {
        Optional<Woning> woning = woningRepository.findById(id);
        if (woning.isPresent()) {
            Woning w = woning.get();
            return transferToDto(w);
        } else {
            throw new RecordNotFoundException("woning not found");
        }
    }
         public WoningDto transferToDto(Woning woning) {
            WoningDto dto = new WoningDto();
            dto.setId(woning.getId());
            dto.setType(woning.getType());
            dto.setName(woning.getName());
            dto.setPrice(woning.getPrice());
            dto.setRented(woning.getRented());
            return dto;
    }
        public Woning transferToWoning(WoningInputDto dto) {

            var woning = new Woning();
            woning.setId(dto.getId());
            woning.setType(dto.getType());
            woning.setName(dto.getName());
            woning.setPrice(dto.getPrice());
            woning.setRented(dto.getRented());
            return woning;

        }
    }


