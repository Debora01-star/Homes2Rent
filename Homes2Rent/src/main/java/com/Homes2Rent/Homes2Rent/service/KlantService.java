package com.Homes2Rent.Homes2Rent.service;

import com.Homes2Rent.Homes2Rent.dto.KlantDto;
import com.Homes2Rent.Homes2Rent.exceptions.DuplicatedEntryException;
import com.Homes2Rent.Homes2Rent.exceptions.RecordNotFoundException;
import com.Homes2Rent.Homes2Rent.model.Klant;
import com.Homes2Rent.Homes2Rent.model.Woning;
import com.Homes2Rent.Homes2Rent.repository.KlantRepository;
import com.Homes2Rent.Homes2Rent.dto.KlantInputDto;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class KlantService {

    private final KlantRepository klantRepos;

    public KlantService(KlantRepository repos) {
        this.klantRepos = repos;
    }

    public KlantDto addKlant(KlantInputDto dto) throws DuplicatedEntryException {
        if (klantRepos.existsByEmail(dto.getEmail())) {
            throw new DuplicatedEntryException("klant email already exists");
        } else {
            Klant klant = transferToKlant(dto);
            klantRepos.save(klant);

            return transferToDto(klant);
        }
    }

    public KlantDto updateKlant(Long id, KlantInputDto dto) throws DuplicatedEntryException {
        if (klantRepos.findById(id).isPresent()) {
            Klant klant = klantRepos.findById(id).get();

            Klant existingKlant = klantRepos.findKlantByEmail(dto.getEmail());

            if (existingKlant != null && !existingKlant.getId().equals(klant.getId())) {
                throw new DuplicatedEntryException("Email already exists");
            } else {
                Klant updateKlant = transferToKlant(dto);
                updateKlant.setId(klant.getId());

                Klant savedKlant = klantRepos.save(updateKlant);

                return transferToDto(savedKlant);
            }
        } else {
            throw new RecordNotFoundException("No klant found");
        }
    }

    public void deleteKlant(Long id) {
        klantRepos.deleteById(id);
    }

    public List<KlantDto> getAllKlanten() {
        List<Klant> klantList = (List<Klant>) klantRepos.findAll();
        List<KlantDto> klantDtoList = new ArrayList<>();
        for (Klant klant : klantList) {
            KlantDto dto = transferToDto(klant);
            klantDtoList.add(dto);
        }
        return klantDtoList;
    }

    public KlantDto getKlant(Long id) {
        Optional<Klant> klant = klantRepos.findById(id);
        if (klant.isPresent()) {
            Klant k = klant.get();
            return transferToDto(k);
        } else {
            throw new RecordNotFoundException("Klant not found");
        }
    }

    public KlantDto transferToDto(Klant klant) {
        KlantDto dto = new KlantDto();
        dto.setEmail(klant.getEmail());
        dto.setFirstname(klant.getFirstname());
        dto.setLastname(klant.getLastname());
        dto.setCity(klant.getCity());
        dto.setAddress(klant.getAddress());
        dto.setZipcode(klant.getZipcode());
        dto.setWoningen(klant.getWoningen());

        return dto;
    }

    public Klant transferToKlant(KlantInputDto dto) {
        Klant klant = new Klant();
        klant.setEmail(dto.email);
        klant.setFirstname(dto.firstname);
        klant.setLastname(dto.lastname);
        klant.setCity(dto.city);
        klant.setAddress(dto.address);
        klant.setZipcode(dto.zipcode);
        klant.setWoningen(dto.getWoningen());

        return klant;
    }

}






