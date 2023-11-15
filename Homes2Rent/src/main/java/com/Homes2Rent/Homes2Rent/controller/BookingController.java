package com.Homes2Rent.Homes2Rent.controller;
import com.Homes2Rent.Homes2Rent.dto.BookingDto;
import com.Homes2Rent.Homes2Rent.dto.BookingInputDto;
import com.Homes2Rent.Homes2Rent.service.BookingService;
import com.Homes2Rent.Homes2Rent.service.HomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/booking")

public class BookingController {


    private final BookingService bookingService;

    private final HomeService homeService;

    public BookingController(BookingService bookingService, HomeService homeService) {
        this.bookingService = bookingService;
        this.homeService = homeService;
    }

    @GetMapping( "")
    public ResponseEntity<List<BookingDto>> getAllBooking() {
        List<BookingDto> dtos = bookingService.getAllBooking();
        return ResponseEntity.ok().body(dtos);

    }

    @GetMapping("{id}")
    public ResponseEntity<BookingDto> getBookingById(@PathVariable("id") Long id) {

        BookingDto booking = bookingService.getBookingById(id);

        return ResponseEntity.ok().body(booking);

    }

    @PostMapping("")
    public ResponseEntity<Object> addBooking(@RequestBody BookingInputDto bookingInputDto) {

        BookingDto dto = bookingService.addBooking(bookingInputDto);

        return ResponseEntity.created(null).body(dto);

    }


    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteBooking(@PathVariable Long id) {

        bookingService.deleteBooking(id);

        return ResponseEntity.noContent().build();

    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateBooking(@PathVariable Long id, @RequestBody BookingInputDto newBooking) {

        BookingDto dto = bookingService.updateBooking(id, newBooking);

        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("{id}/receipt/{receipt_id}")
    public void assignReceiptToBooking(@PathVariable("id") Long id, @PathVariable ("receipt_id") Long factuur_id) {
        bookingService.assignReceiptToBooking(id, factuur_id);
    }

    @PutMapping("/{id}/{homeId}")
    public void assignHomeToBooking(@PathVariable("id") Long id, @PathVariable("homeId") Long homeId) {
        bookingService.assignHomeToBooking(id, homeId);
    }

    @PutMapping("/booking/{id}/{cancellationId}")
    public void assignCancellationToBooking(@PathVariable("id") Long id, @PathVariable("cancellationId") Long cancellationId) {
        bookingService.assignCancellationToBooking(id, cancellationId);
    }
}





