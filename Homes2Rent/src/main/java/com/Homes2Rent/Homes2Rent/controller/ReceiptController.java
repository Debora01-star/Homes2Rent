package com.Homes2Rent.Homes2Rent.controller;
import com.Homes2Rent.Homes2Rent.dto.ReceiptDto;
import org.springframework.web.bind.annotation.*;
import com.Homes2Rent.Homes2Rent.service.ReceiptService;
import java.util.List;

@RestController

public class ReceiptController {

    private final ReceiptService receiptService;;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }


    @GetMapping("/receipt")
    public List<ReceiptDto> getAllReceipt() {

        List<ReceiptDto> dtos = receiptService.getAllReceipt();

        return dtos;
    }


    @GetMapping("/receipt/{id}")
    public ReceiptDto getReceipt(@PathVariable("id") Long id) {

        ReceiptDto dto = receiptService.getReceipt(id);

        return dto;
    }

    @PostMapping("/receipt")
    public ReceiptDto addReceipt(@RequestBody ReceiptDto dto) {
        ReceiptDto dto1 = receiptService.addReceipt(dto);
        return dto1;
    }

    @DeleteMapping("/receipt/{id}")
    public void deleteReceipt(@PathVariable("id") Long id) {
        receiptService.deleteReceipt(id);
    }

    @PutMapping("/receipt/{id}")
    public ReceiptDto updateReceipt(@PathVariable("id") Long id, @RequestBody ReceiptDto dto) {
        receiptService.updateReceipt(id, dto);
        return dto;
    }

}



