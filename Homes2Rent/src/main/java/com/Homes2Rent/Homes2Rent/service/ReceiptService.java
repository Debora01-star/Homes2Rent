package com.Homes2Rent.Homes2Rent.service;
import com.Homes2Rent.Homes2Rent.dto.ReceiptDto;
import com.Homes2Rent.Homes2Rent.exceptions.RecordNotFoundException;
import com.Homes2Rent.Homes2Rent.model.Receipt;
import com.Homes2Rent.Homes2Rent.repository.ReceiptRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@Service
public class ReceiptService {

    private final ReceiptRepository receiptRepository;
    public ReceiptService(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;


    }
    public List<ReceiptDto> getAllReceipt() {
        List<ReceiptDto> dtos = new ArrayList<>();
        List<Receipt> receipts = receiptRepository.findAll();
        for (Receipt re : receipts) {
            dtos.add(transferToDto(re));
        }
        return dtos;

    }

    public ReceiptDto getReceipt(long id) {
        Optional<Receipt> receipt = receiptRepository.findById(id);
        if (receipt.isPresent()) {
            return transferToDto(receipt.get());
        } else {
            throw new RecordNotFoundException("No receipt found");
        }
    }

    public ReceiptDto addReceipt(ReceiptDto receiptDto) {
        Receipt re =  transferToReceipt(receiptDto);
        receiptRepository.save(re);
        return receiptDto;
    }

    public void deleteReceipt(Long id) {
        receiptRepository.deleteById(id);
    }

    public void updateReceipt(Long id, ReceiptDto receiptDto) {
        if(!receiptRepository.existsById(id)) {
            throw new RecordNotFoundException("No receipt found");
        }
        Receipt receipt1 = receiptRepository.findById(id).orElse(null);

        receipt1.setId(receiptDto.getId());
        receipt1.setPrice(receiptDto.getPrice());
        receiptRepository.save(receipt1);
    }

    public ReceiptDto transferToDto(Receipt receipt){
        var dto = new ReceiptDto();

        dto.id = receipt.getId();
        dto.price = receipt.getPrice();

        return dto;
    }
    public Receipt transferToReceipt(ReceiptDto dto) {
        var receipt = new Receipt();

        receipt.setId(dto.getId());
        receipt.setPrice(dto.getPrice());

        return receipt;
    }


}
