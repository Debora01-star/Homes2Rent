package com.Homes2Rent.Homes2Rent.service;
import com.Homes2Rent.Homes2Rent.dto.CancellationDto;
import com.Homes2Rent.Homes2Rent.dto.CancellationInputDto;
import com.Homes2Rent.Homes2Rent.exceptions.RecordNotFoundException;
import com.Homes2Rent.Homes2Rent.model.Cancellation;
import com.Homes2Rent.Homes2Rent.repository.CancellationRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@Service
public class CancellationService {


    private final CancellationRepository cancellationRepository;


    public CancellationService(CancellationRepository cancellationRepository) {
        this.cancellationRepository = cancellationRepository;
    }

    public List<CancellationDto> getAllCancellation() {
        List<Cancellation> cancellations = cancellationRepository.findAll();
        List<CancellationDto> dtos = new ArrayList<>();
        for (Cancellation ca : cancellations) {
            dtos.add(transferToDto((Cancellation) cancellations));
        }
        return dtos;
    }

    public CancellationDto getCancellation(long id) {
        Optional<Cancellation> cancellation = cancellationRepository.findById(id);
        if(cancellation.isPresent()) {
            CancellationDto ca = transferToDto(cancellation.get());
            return ca;
        } else {
            throw new RecordNotFoundException("No cancellation found");
        }
    }

    public CancellationDto addCancellation(CancellationDto cancellationDto) {
        cancellationRepository.save(transfertoCancellation(new CancellationInputDto()));
        return cancellationDto;
    }
    public void deleteCancellation(Long id) {
        cancellationRepository.deleteById(id);
    }

    public void updateCancellation(Long id, CancellationDto cancellationDto) {
        if(!cancellationRepository.existsById(id)) {
            throw new RecordNotFoundException("No cancellation found");
        }

        Cancellation cancellation1 = cancellationRepository.findById(id).orElse(null);


            cancellation1.setId(cancellationDto.getId());
            cancellation1.setFinish_date(cancellationDto.getFinish_date());
            cancellation1.setStatus(cancellationDto.getStatus());
            cancellation1.setType_booking(cancellationDto.getType_booking());
            cancellation1.setPrice(cancellationDto.getPrice());
            cancellation1.setName(cancellationDto.getName());
            cancellation1.setHome(cancellationDto.getHome());

            Cancellation returnCancellation = cancellationRepository.save(cancellation1);
        cancellationRepository.save(cancellation1);
    }

    public Cancellation transfertoCancellation(CancellationInputDto dto) {
        var cancellation = new Cancellation();

        cancellation.setId(dto.getId());
        cancellation.setFinish_date(dto.getFinish_date());
        cancellation.setPrice(dto.getPrice());
        cancellation.setStatus(dto.getStatus());
        cancellation.setType_booking(dto.getType_booking());
        cancellation.setName(dto.getName());
        cancellation.setHome(dto.getHome());

        return cancellation;
    }

    public CancellationDto transferToDto(Cancellation cancellation) {
        CancellationDto dto = new CancellationDto();

        dto.setId(cancellation.getId());
        dto.setFinish_date(cancellation.getFinish_date());
        dto.setPrice(cancellation.getPrice());
        dto.setName(cancellation.getName());
        dto.setStatus((cancellation.getStatus()));
        dto.setType_booking((cancellation.getType_boeking()));
        dto.setHome((cancellation.getHome()));

        return dto;
    }
}