package com.Homes2Rent.Homes2Rent.service;

import com.Homes2Rent.Homes2Rent.dto.AnnuleringDto;
import com.Homes2Rent.Homes2Rent.dto.AnnuleringInputDto;
import com.Homes2Rent.Homes2Rent.exceptions.RecordNotFoundException;
import com.Homes2Rent.Homes2Rent.model.Annulering;
import com.Homes2Rent.Homes2Rent.repository.AnnuleringRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AnnuleringsService {


    private final AnnuleringRepository annuleringRepository;

    public AnnuleringsService(AnnuleringRepository annuleringRepository) {
        this.annuleringRepository = annuleringRepository;
    }


    public List<AnnuleringDto> getAllAnnuleringen() {
        List<Annulering> annuleringList = (List<Annulering>) annuleringRepository.findAll();
        List<AnnuleringDto> annuleringDtoList = new ArrayList<>();

        for (Annulering annulering : annuleringList) {
            AnnuleringDto dto = transferToDto(annulering);
            annuleringDtoList.add(dto);
        }
        return annuleringDtoList;
    }


    public List<AnnuleringDto> getAllAnnuleringen(String Id) {
        List<Annulering> annuleringList = annuleringRepository.findAllAnnuleringByIdEqualsIgnoreCase(Id);
        List<AnnuleringDto> annuleringDtoList = new ArrayList<>();

        for (Annulering annulering : annuleringList) {
            AnnuleringDto dto = transferToDto(annulering);
            annuleringDtoList.add(dto);
        }
        return annuleringDtoList;
    }

    public AnnuleringDto getAnnuleringById(Long id) throws RecordNotFoundException {
        Optional<Annulering> annuleringOptional = annuleringRepository.findById(id);
        if (annuleringOptional.isPresent()) {
            Annulering annulering = annuleringOptional.get();
            return transferToDto(annulering);
        } else {
            throw new RecordNotFoundException("geen annulering gevonden");
        }
    }

    public AnnuleringDto addAnnulering(AnnuleringInputDto dto) {

        Annulering annulering = transfertoAnnulering(dto);
        annuleringRepository.save(annulering);

        return transferToDto(annulering);
    }

    public void deleteAnnulering(@RequestBody Long id) {

        annuleringRepository.deleteById(id);

    }

    public AnnuleringDto updateAnnulering(Long id, AnnuleringInputDto newAnnulering) throws RecordNotFoundException {

        Optional<Annulering> annuleringOptional = annuleringRepository.findById(id);
        if (annuleringOptional.isPresent()) {

            Annulering annulering1 = annuleringOptional.get();


            annulering1.setId(newAnnulering.getId());
            annulering1.setFinish_date(newAnnulering.getFinish_date());
            annulering1.setNotes(newAnnulering.getNotes());
            annulering1.setStatus(newAnnulering.getStatus());
            annulering1.setType_boeking(newAnnulering.getType_boeking());
            annulering1.setPrice(newAnnulering.getPrice());
            annulering1.setName(newAnnulering.getName());
            annulering1.setWoning(newAnnulering.getWoning());

            Annulering returnAnnulering = annuleringRepository.save(annulering1);

            return transferToDto(returnAnnulering);

        } else {

            throw new RecordNotFoundException("geen annulering gevonden");

        }

    }

    public Annulering transfertoAnnulering(AnnuleringInputDto dto) {
        var annulering = new Annulering();

        annulering.setId(dto.getId());
        annulering.setFinish_date(dto.getFinish_date());
        annulering.setPrice(dto.getPrice());
        annulering.setNotes(dto.getNotes());
        annulering.setStatus(dto.getStatus());
        annulering.setType_boeking(dto.getType_boeking());
        annulering.setName(dto.getName());
        annulering.setWoning(dto.getWoning());

        return annulering;
    }

    public AnnuleringDto transferToDto(Annulering annulering) {
        AnnuleringDto dto = new AnnuleringDto();

        dto.setId(annulering.getId());
        dto.setFinish_date(annulering.getFinish_date());
        dto.setPrice(annulering.getPrice());
        dto.setName(annulering.getName());
        dto.setNotes(annulering.getNotes());
        dto.setStatus((annulering.getStatus()));
        dto.setType_boeking((annulering.getType_boeking()));
        dto.setWoning((annulering.getWoning()));

        return dto;
    }
}