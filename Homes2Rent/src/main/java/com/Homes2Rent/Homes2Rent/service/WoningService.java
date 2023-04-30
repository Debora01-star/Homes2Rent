package com.Homes2Rent.Homes2Rent.service;
import com.Homes2Rent.Homes2Rent.dto.WoningDto;
import com.Homes2Rent.Homes2Rent.exceptions.RecordNotFoundException;
import com.Homes2Rent.Homes2Rent.model.Woning;
import com.Homes2Rent.Homes2Rent.repository.WoningRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@Service
public class WoningService {


        private WoningRepository woningRepository;

        public WoningService(WoningRepository woningRepository)

        {
            this.woningRepository = woningRepository;

        }

    public List<WoningDto> getAllWoningen() {
        List<Woning> woningList = woningRepository.findAll();
        List<WoningDto> dtos = new ArrayList<>();
        for (Woning woning : woningList) {
            dtos.add(transferToDto(woning));
        }
        return dtos;
    }

    public WoningDto getWoning(Long id) {
        Optional<Woning> woning = woningRepository.findById(id);
        if(woning.isPresent()) {
            WoningDto dto = transferToDto(woning.get());
            return dto;
        } else {
            throw new RecordNotFoundException("No woning found");
        }
    }

    public WoningDto addWoning(WoningDto woningDto) {
        Woning woning = transferToWoning(woningDto);
        woningRepository.save(woning);
        return transferToDto(woning);
    }


    public void deleteWoning(Long id) {
        woningRepository.deleteById(id);
    }

    public void updateWoning(Long id, WoningDto woningDto) {
        if(!woningRepository.existsById(id)) {
            throw new RecordNotFoundException("No woning found");
        }
        Woning woning = woningRepository.findById(id).orElse(null);

        woning.setId(woning.getId());
        woning.setRented(woning.getRented());
        woning.setName(woning.getName());
        woning.setPrice(woning.getPrice());
        woningRepository.save(woning);

    }

    public WoningDto transferToDto(Woning woning){
        var dto = new WoningDto();

        dto.setId(woning.getId());
        dto.setType(woning.getType());
        dto.setName(woning.getName());
        dto.setRented(woning.getRented());
        dto.setPrice(woning.getPrice());

        return dto;
    }

    public Woning transferToWoning(WoningDto woningDto){
        Woning woning = new Woning();

        woning.setId(woningDto.getId());
        woning.setType(woningDto.getType());
        woning.setName(woningDto.getName());
        woning.setRented(woningDto.getRented());
        woning.setPrice(woningDto.getPrice());

        return woning;
    }



}

