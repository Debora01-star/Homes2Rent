package com.Homes2Rent.Homes2Rent.service;

import com.Homes2Rent.Homes2Rent.dto.AnnuleringDto;
import com.Homes2Rent.Homes2Rent.dto.AnnuleringInputDto;
import com.Homes2Rent.Homes2Rent.exceptions.RecordNotFoundException;
import com.Homes2Rent.Homes2Rent.model.Annulering;
import com.Homes2Rent.Homes2Rent.repository.AnnuleringRepository;
import org.springframework.stereotype.Service;

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
        List<Annulering> annuleringen = annuleringRepository.findAll();
        List<AnnuleringDto> dtos = new ArrayList<>();
        for (Annulering ci : annuleringen) {
            dtos.add(transferToDto((Annulering) annuleringen));
        }
        return dtos;
    }

    public AnnuleringDto getAnnulering(long id) {
        Optional<Annulering> annulering = annuleringRepository.findById(id);
        if(annulering.isPresent()) {
            AnnuleringDto ann = transferToDto(annulering.get());
            return ann;
        } else {
            throw new RecordNotFoundException("No annulering found");
        }
    }

    public AnnuleringDto addAnnulering(AnnuleringDto annuleringDto) {
        annuleringRepository.save(transfertoAnnulering(new AnnuleringInputDto()));
        return annuleringDto;
    }
    public void deleteAnnulering(Long id) {
        annuleringRepository.deleteById(id);
    }

    public void updateAnnulering(Long id, AnnuleringDto annuleringDto) {
        if(!annuleringRepository.existsById(id)) {
            throw new RecordNotFoundException("No annulering found");
        }

        Annulering annulering1 = annuleringRepository.findById(id).orElse(null);


            annulering1.setId(annuleringDto.getId());
            annulering1.setFinish_date(annuleringDto.getFinish_date());
            annulering1.setStatus(annuleringDto.getStatus());
            annulering1.setType_boeking(annuleringDto.getType_boeking());
            annulering1.setPrice(annuleringDto.getPrice());
            annulering1.setName(annuleringDto.getName());
            annulering1.setWoning(annuleringDto.getWoning());

            Annulering returnAnnulering = annuleringRepository.save(annulering1);
        annuleringRepository.save(annulering1);
    }

    public Annulering transfertoAnnulering(AnnuleringInputDto dto) {
        var annulering = new Annulering();

        annulering.setId(dto.getId());
        annulering.setFinish_date(dto.getFinish_date());
        annulering.setPrice(dto.getPrice());
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
        dto.setStatus((annulering.getStatus()));
        dto.setType_boeking((annulering.getType_boeking()));
        dto.setWoning((annulering.getWoning()));

        return dto;
    }
}