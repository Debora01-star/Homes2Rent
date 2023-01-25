package com.Homes2Rent.Homes2Rent.service;

import com.Homes2Rent.Homes2Rent.dto.FactuurDto;
import com.Homes2Rent.Homes2Rent.dto.FactuurInputDto;
import com.Homes2Rent.Homes2Rent.exceptions.RecordNotFoundException;
import com.Homes2Rent.Homes2Rent.model.Factuur;
import com.Homes2Rent.Homes2Rent.repository.FactuurRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class FactuurService {


    // We importeren de repository nu in de service in plaats van in de controller.
    // dit mag met constructor injection of autowire.
    private final FactuurRepository factuurRepository;


    public FactuurService(FactuurRepository factuurRepository) {
        this.factuurRepository = factuurRepository;


    }

    // Vanuit de repository kunnen we een lijst van Televisions krijgen, maar de communicatie container tussen Service en
    // Controller is de Dto. We moeten de Televisions dus vertalen naar TelevisionDtos. Dit moet een voor een, omdat
    // de translateToDto() methode geen lijst accepteert als argument, dus gebruiken we een for-loop.
    public List<FactuurDto> getAllFactuur() {
        List<Factuur> factuurList = (List<Factuur>) factuurRepository.findAll();
        List<FactuurDto> factuurDtoList = new ArrayList<>();

        for (Factuur factuur : factuurList) {
            FactuurDto dto = transferToDto(factuur);
            factuurDtoList.add(dto);
        }
        return factuurDtoList;
    }

    // Vanuit de repository kunnen we een lijst van Televisions met een bepaalde brand krijgen, maar de communicatie
    // container tussen Service en Controller is de Dto. We moeten de Televisions dus vertalen naar TelevisionDtos. Dit
    // moet een voor een, omdat de translateToDto() methode geen lijst accepteert als argument, dus gebruiken we een for-loop.
    public List<FactuurDto> getAllFactuurenById(String Id) {
        List<Factuur> factuurList = factuurRepository.findAllFacturenByIdEqualsIgnoreCase(Id);
        List<FactuurDto> factuurDtoList = new ArrayList<>();

        for (Factuur factuur : factuurList) {
            FactuurDto dto = transferToDto(factuur);
            factuurDtoList.add(dto);
        }
        return factuurDtoList;
    }

    // Deze methode is inhoudelijk hetzelfde als het was in de vorige opdracht. Wat verandert is, is dat we nu checken
    // op optional.isPresent in plaats van optional.isEmpty en we returnen een TelevisionDto in plaats van een Television.
    public FactuurDto getFactuurById(Long id) throws RecordNotFoundException {
        Optional<Factuur> factuurOptional = factuurRepository.findById(id);
        if (factuurOptional.isPresent()) {
            Factuur factuur = factuurOptional.get();
            return transferToDto(factuur);
        } else {
            throw new RecordNotFoundException("geen factuur gevonden");
        }
    }

    // In deze methode moeten we twee keer een vertaal methode toepassen.
    // De eerste keer van dto naar televsion, omdat de parameter een dto is.
    // De tweede keer van television naar dto, omdat de return waarde een dto is.
    public FactuurDto addFactuur(FactuurInputDto dto) {

        Factuur factuur = transferToFactuur(dto);
        factuurRepository.save(factuur);

        return transferToDto(factuur);
    }

    // Deze methode is inhoudelijk neit veranderd. Het is alleen verplaatst naar de Service laag.
    public void deleteFactuur(@RequestBody Long id) {

        factuurRepository.deleteById(id);

    }

    // Deze methode is inhoudelijk niet veranderd, alleen staat het nu in de Service laag en worden er Dto's en
    // vertaal methodes gebruikt.
    public FactuurDto updateFactuur(Long id, FactuurInputDto newFactuur) throws RecordNotFoundException {

        Optional<Factuur> factuurOptional = factuurRepository.findById(id);
        if (factuurOptional.isPresent()) {

            Factuur factuur1 = factuurOptional.get();
            factuur1.setId(newFactuur.getId());
            factuur1.setKlant(newFactuur.getKlant());
            factuur1.setBoeking(newFactuur.getBoeking());
            factuur1.setPrice(newFactuur.getPrice());

            return transferToDto(factuur1);

        } else {

            throw new RecordNotFoundException("geen factuur gevonden");

        }

    }

    // Dit is de vertaal methode van TelevisionInputDto naar Television.
    public Factuur transferToFactuur(FactuurInputDto dto) {
        var factuur = new Factuur();

        factuur.setId(dto.getId());
        factuur.setKlant(dto.getKlant());
        factuur.setBoeking(dto.getBoeking());
        factuur.setPrice(dto.getPrice());

        return factuur;
    }

    // Dit is de vertaal methode van Television naar TelevisionDto
    public FactuurDto transferToDto(Factuur factuur) {
        FactuurDto dto = new FactuurDto();
        dto.setId(factuur.getId());
        dto.setKlant(factuur.getKlant());
        dto.setBoeking(factuur.getBoeking());
        dto.setPrice(factuur.getPrice());

        return dto;
    }
}
