package com.Homes2Rent.Homes2Rent.service;
import com.Homes2Rent.Homes2Rent.dto.BookingDto;
import com.Homes2Rent.Homes2Rent.dto.BookingInputDto;
import com.Homes2Rent.Homes2Rent.dto.CancellationDto;
import com.Homes2Rent.Homes2Rent.exceptions.RecordNotFoundException;
import com.Homes2Rent.Homes2Rent.model.Booking;
import com.Homes2Rent.Homes2Rent.model.Receipt;
import com.Homes2Rent.Homes2Rent.repository.*;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {


    private final BookingRepository bookingRepository;

    private final ReceiptRepository receiptRepository;

    private final ReceiptService receiptService;

    private final HomeRepository homeRepository;

    private final HomeService homeService;

    private final CancellationRepository cancellationRepository;
    private final CancellationService cancellationService;



    public BookingService(BookingRepository bookingRepository, ReceiptRepository receiptRepository, ReceiptService receiptService, HomeRepository homeRepository, HomeService homeService, CancellationRepository cancellationRepository, CancellationService cancellationService) {
        this.bookingRepository = bookingRepository;
        this.receiptRepository = receiptRepository;
        this.receiptService = receiptService;
        this.homeRepository = homeRepository;
        this.homeService = homeService;
        this.cancellationRepository = cancellationRepository;
        this.cancellationService = cancellationService;
    }


    public List<BookingDto> getAllBooking() {
        List<Booking> bookingList = bookingRepository.findAll();
        return transferBookingListToDtoList(bookingList);
    }


    public List<BookingDto> transferBookingListToDtoList(List<Booking> bookingList){
        List<BookingDto> bookingDtoList = new ArrayList<>();

        for(Booking booking : bookingList) {
            BookingDto dto = transferToDto(booking);
            if(booking.getCancellation() != null){
                dto.setCancellationDto(cancellationService.transferToDto(booking.getCancellation()));
            }
            if (booking.getReceipt() != null) {
                dto.setReceiptDto(receiptService.transferToDto(new Receipt().getReceipt()));
            }
            bookingDtoList.add(dto);
        }
        return bookingDtoList;
    }


    public BookingDto getBookingById(Long id) {

        if (bookingRepository.findById(id).isPresent()){
            Booking booking = bookingRepository.findById(id).get();
            BookingDto dto =transferToDto(booking);
            if(booking.getCancellation() != null){
                dto.setCancellationDto(cancellationService.transferToDto(booking.getCancellation()));
            }
            if(booking.getReceipt() != null){
                dto.setReceiptDto(receiptService.transferToDto(new Receipt().getReceipt()));
            }

            return transferToDto(booking);
        } else {
            throw new RecordNotFoundException("no booking found");
        }
    }

    private BookingDto transferToDto(Booking booking) {

        BookingDto dto = new BookingDto();

        dto.setId(booking.getId());
        dto.setType_booking(booking.getType_booking());
        dto.setFinish_date(booking.getFinish_date());
        dto.setStatus(booking.getStatus());
        dto.setHome(booking.getHome());
        dto.setPrice(booking.getPrice());

        return dto;

    }

    public BookingDto addBooking(BookingInputDto bookingInputdto) {

        Booking booking = transferToBooking(bookingInputdto);
        bookingRepository.save(booking);

        return transferToDto(booking);
    }


    public void deleteBooking(Long id) {

        bookingRepository.deleteById(id);

    }
    public BookingDto updateBooking(Long id, BookingInputDto InputDto) {

        if(bookingRepository.findById(id).isEmpty()){
            throw new RecordNotFoundException("no booking found");
        } else {
            Booking booking = bookingRepository.findById(id).get();

            Booking booking1 = transferToBooking(InputDto);
            booking1.setId(booking.getId());

            bookingRepository.save(booking1);

            return transferToDto(booking1);

        }
    }

    Booking transferToBooking(BookingInputDto inputDto) {

        var booking = new Booking();

        booking.setType_booking(inputDto.getType_booking());
        booking.setFinish_date(inputDto.getFinish_date());
        booking.setStatus(inputDto.getStatus());
        booking.setHome(inputDto.getHome());
        booking.setPrice(inputDto.getPrice());
        booking.setPrice(inputDto.getPrice());

        return booking;

    }

    public void assignReceiptToBooking(Long id, Long factuurId) {
            var optionalBooking = bookingRepository.findById(id);
            var optionalReceipt = receiptRepository.findById(factuurId);

            if(optionalBooking.isPresent() && optionalReceipt.isPresent()) {
                var booking = optionalBooking.get();
                var receipt = optionalReceipt.get();

                booking.setReceipt(receipt);
                bookingRepository.save(booking);
            } else {
                throw new RecordNotFoundException();
            }
        }

        public void assignCancellationToBooking(Long id, Long cancellationId) {
            var optionalBooking = bookingRepository.findById(id);
            var optionalCancellation = cancellationRepository.findById(cancellationId);

            if(optionalBooking.isPresent() && optionalCancellation.isPresent()) {
                var booking = optionalBooking.get();
                var cancellation = optionalCancellation.get();

                booking.setCancellation(cancellation);
                bookingRepository.save(booking);
            } else {
                throw new RecordNotFoundException();
            }
        }

    public void assignHomeToBooking(Long id, Long homeId) {

        var optionalBooking = bookingRepository.findById(id);
        var optionalHome = homeRepository.findById(homeId);

        if(optionalBooking.isPresent() && optionalHome.isPresent()) {
            var booking = optionalBooking.get();
            var home = optionalHome.get();

            booking.setHome(home);
            bookingRepository.save(booking);
        } else {
            throw new RecordNotFoundException();
        }
    }

}








