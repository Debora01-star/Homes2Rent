package com.Homes2Rent.Homes2Rent.service;
import com.Homes2Rent.Homes2Rent.dto.FactuurDto;
import com.Homes2Rent.Homes2Rent.exceptions.RecordNotFoundException;
import com.Homes2Rent.Homes2Rent.model.Factuur;
import com.Homes2Rent.Homes2Rent.repository.FactuurRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@Service
public class FactuurService {

    private final FactuurRepository factuurRepository;
    public FactuurService(FactuurRepository factuurRepository) {
        this.factuurRepository = factuurRepository;


    }
    public List<FactuurDto> getAllFacturen() {
        List<FactuurDto> dtos = new ArrayList<>();
        List<Factuur> facturen = factuurRepository.findAll();
        for (Factuur fa : facturen) {
            dtos.add(transferToDto(fa));
        }
        return dtos;

    }

    public FactuurDto getFactuur(long id) {
        Optional<Factuur> factuur = factuurRepository.findById(id);
        if (factuur.isPresent()) {
            return transferToDto(factuur.get());
        } else {
            throw new RecordNotFoundException("No factuur found");
        }
    }

    public FactuurDto addFactuur(FactuurDto factuurDto) {
        Factuur fa =  transferToFactuur(factuurDto);
        factuurRepository.save(fa);
        return factuurDto;
    }

    public void deleteFactuur(Long id) {
        factuurRepository.deleteById(id);
    }

    public void updateFactuur(Long id, FactuurDto factuurDto) {
        if(!factuurRepository.existsById(id)) {
            throw new RecordNotFoundException("No factuur found");
        }
        Factuur factuur1 = factuurRepository.findById(id).orElse(null);

        factuur1.setId(factuurDto.getId());
        factuur1.setPrice(factuurDto.getPrice());
        factuurRepository.save(factuur1);
    }

    public FactuurDto transferToDto(Factuur factuur){
        var dto = new FactuurDto();

        dto.id = factuur.getId();
        dto.price = factuur.getPrice();

        return dto;
    }
    public Factuur transferToFactuur(FactuurDto dto) {
        var factuur = new Factuur();

        factuur.setId(dto.getId());
        factuur.setPrice(dto.getPrice());

        return factuur;
    }


}
