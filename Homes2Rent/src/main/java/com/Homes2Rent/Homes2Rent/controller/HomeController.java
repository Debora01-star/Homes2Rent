package com.Homes2Rent.Homes2Rent.controller;
import com.Homes2Rent.Homes2Rent.dto.HomeDto;
import com.Homes2Rent.Homes2Rent.service.BookingService;
import com.Homes2Rent.Homes2Rent.service.HomeService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class HomeController {

    private final HomeService  homeService;

    private final BookingService bookingService;

    public HomeController( HomeService homeService, BookingService bookingService1) {
        this.homeService = homeService;
        this.bookingService = bookingService1;
    }

    @GetMapping("/woningen")
    public List<HomeDto> getAllWoningen() {

        List<HomeDto> woningen = homeService.getAllHome();

        return woningen;
    }

    @GetMapping("/woningen/{id}")
    public HomeDto getWoning(@PathVariable("id") Long id) {

        HomeDto homeDto = homeService.getHome(id);

        return homeDto;
    }

    @PostMapping("/woningen")
    public HomeDto addWoning(@RequestBody HomeDto homeDto) {

        HomeDto dto1 = homeService.addHome(homeDto);

        return dto1;
    }

    @DeleteMapping("/woningen/{id}")
    public void deleteWoning(@PathVariable("id") Long id) {
        homeService.deleteHome(id);
    }

    @PutMapping("/woningen/{id}")
    public HomeDto updateWoning(@PathVariable("id") Long id, @RequestBody HomeDto dto) {
        homeService.updateHome(id, dto);
        return dto;
    }

//    @GetMapping("/woningen/boekingen/{woningId}")
//    public BookingDto getBoekingByWoningId(@PathVariable("woningId") Long woningId){
//        return boekingService.getBoekingById(woningId);
//    }
}



