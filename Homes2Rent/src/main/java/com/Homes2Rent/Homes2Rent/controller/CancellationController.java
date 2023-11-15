package com.Homes2Rent.Homes2Rent.controller;
import com.Homes2Rent.Homes2Rent.dto.CancellationDto;
import org.springframework.web.bind.annotation.*;
import com.Homes2Rent.Homes2Rent.service.CancellationService;
import java.util.List;


@RestController

public class CancellationController {

    private final CancellationService cancellationService;

    public CancellationController(CancellationService cancellationService) {
        this.cancellationService = cancellationService;
    }

    @GetMapping("/cancellation")
    public List<CancellationDto> getAllCancellation() {

        var dtos = cancellationService.getAllCancellation();

        return dtos;
    }

    @GetMapping("/cancellation/{id}")
    public CancellationDto getCancellation(@PathVariable("id") Long id) {

        CancellationDto cancellationDto = cancellationService.getCancellation(id);

        return cancellationDto;
    }

    @PostMapping("/cancellation")
    public CancellationDto addCancellation(@RequestBody CancellationDto dto) {
        CancellationDto cancellationDto = cancellationService.addCancellation(dto);
        return cancellationDto;
    }

    @DeleteMapping("/cancellation/{id}")
    public void deleteCancellation(@PathVariable("id") Long id) {
        cancellationService.deleteCancellation(id);

    }

    @PutMapping("/cancellation/{id}")
    public CancellationDto updateCancellation(@PathVariable("id") Long id, @RequestBody CancellationDto cancellationDto) {
        cancellationService.updateCancellation(id, cancellationDto);
        return cancellationDto;
    }


}

