package com.Homes2Rent.Homes2Rent.service;

import com.Homes2Rent.Homes2Rent.dto.BookingDto;
import com.Homes2Rent.Homes2Rent.dto.BookingInputDto;
import com.Homes2Rent.Homes2Rent.exceptions.RecordNotFoundException;
import com.Homes2Rent.Homes2Rent.model.Booking;
import com.Homes2Rent.Homes2Rent.model.Cancellation;
import com.Homes2Rent.Homes2Rent.model.Home;
import com.Homes2Rent.Homes2Rent.model.Receipt;
import com.Homes2Rent.Homes2Rent.repository.BookingRepository;
import com.Homes2Rent.Homes2Rent.repository.HomeRepository;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BookingServiceTest {

    @Mock
    BookingRepository bookingRepository;

    @Mock
    HomeRepository homeRepository;

    @InjectMocks
    BookingService bookingService;

    @Captor
    ArgumentCaptor<Booking> argumentCaptor;

    Booking booking1;
    Booking booking2;

    Home home1;
    Home home2;

    @BeforeEach
    void setUp() {
        home1 = new Home(1L, "huis", "villa happiness", 1500, "rented");
        home2 = new Home(1L, "appartament", "cozy rooms", 750, "rented");

        booking1 = new Booking(1L, LocalDate.of(2023, 03, 15), "status", "geboekt", 1500, home1);
        booking2 = new Booking(2L, LocalDate.of(2023, 03, 21), "status", "geboekt", 750, home2);
    }

    @Test
    void createBooking() {
        BookingInputDto dto = new BookingInputDto(LocalDate.of(2023, 03, 15), 1L, "status", "geboekt", home1, 1500);

        given(homeRepository.findById(1L)).willReturn(Optional.of(home1));
        when(bookingRepository.save(booking1)).thenReturn(booking1);

        bookingService.addBooking(dto);
        verify(bookingRepository, times(1)).save(argumentCaptor.capture());
        Booking booking = argumentCaptor.getValue();

        assertEquals(booking1.getType_booking(), booking.getType_booking());
        assertEquals(booking1.getStatus(), booking.getStatus());
        assertEquals(booking1.getFinish_date(), booking.getFinish_date());
        assertEquals(booking1.getPrice(), booking.getPrice());
        assertEquals(booking1.getHome(), booking.getHome());

    }

    /**
     * Method under test: {@link BookingService#updateBooking(Long, BookingInputDto)}
     */
    @Test
    void testUpdateBooking() {
        assertThrows(RecordNotFoundException.class, () -> bookingService.updateBooking(123L, new BookingInputDto()));
    }

    @Test
    void updateBooking() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking1));
        given(homeRepository.findById(1L)).willReturn(Optional.of(home1));
        BookingInputDto dto = new BookingInputDto(LocalDate.of(2023, 03, 15), 1L, "status", "geboekt", home1, 1500);

        when(bookingRepository.save(bookingService.transferToBooking(dto))).thenReturn(booking1);

        bookingService.updateBooking(1L, dto);

        verify(bookingRepository, times(1)).save(argumentCaptor.capture());

        Booking captured = argumentCaptor.getValue();

        assertEquals(dto.getType_booking(), captured.getType_booking());
        assertEquals(dto.getStatus(), captured.getStatus());
        assertEquals(dto.getFinish_date(), captured.getFinish_date());
        assertEquals(dto.getId(), captured.getId());
        assertEquals(dto.getPrice(), captured.getPrice());
        assertEquals(dto.getHome(), captured.getHome());


    }

    @Test
    void deleteBooking() {
        bookingService.deleteBooking(1L);

        verify(bookingRepository).deleteById(1L);
    }


    @Test
    void testDeleteBooking2() {
        bookingService.deleteBooking(1L);
        assertEquals(77, bookingService.getAllBooking().size());
    }

    @Test
    void getAllBookingen() {

        when(bookingRepository.findAll()).thenReturn(List.of(booking1, booking2));

        List<Booking> booking = (List<Booking>) bookingRepository.findAll();
        List<BookingDto> dtos = bookingService.getAllBooking();

        assertEquals(booking.get(0).getType_booking(), dtos.get(0).getType_booking());
        assertEquals(booking.get(0).getFinish_date(), dtos.get(0).getFinish_date());
        assertEquals(booking.get(0).getStatus(), dtos.get(0).getStatus());
        assertEquals(booking.get(0).getHome(), dtos.get(0).getHome());
        assertEquals(booking.get(0).getId(), dtos.get(0).getId());
        assertEquals(booking.get(0).getPrice(), dtos.get(0).getPrice());
    }

    /**
     * Method under test: {@link BookingService#getAllBooking()}
     */
    @Test
    void testGetAllBookingen() {
        assertEquals(77, bookingService.getAllBooking().size());
    }

    /**
     * Method under test: {@link BookingService#transferBookingListToDtoList(List)}
     */
    @Test
    void testTransferBookingListToDtoList() {
        assertTrue(bookingService.transferBookingListToDtoList(new ArrayList<>()).isEmpty());
    }

    /**
     * Method under test: {@link BookingService#transferBookingListToDtoList(List)}
     */
    @Test
    void testTransferBookingListToDtoList2() {
        Home home = new Home();
        home.setBooking(new ArrayList<>());
        home.setId(123L);
        home.setName("Name");
        home.setPrice(1);
        home.setRented("Rented");
        home.setType("Type");

        Cancellation cancellation = new Cancellation();
        cancellation.setFinish_date(LocalDate.ofEpochDay(1L));
        cancellation.setId(123L);
        cancellation.setName("Name");
        cancellation.setPrice(1);
        cancellation.setStatus("Status");
        cancellation.setType_booking("Type booking");
        cancellation.setHome(home);

        Receipt receipt = new Receipt();
        receipt.setId(123L);
        receipt.setKlant("Klant");
        receipt.setPrice(1);

        Home home1 = new Home();
        home1.setBooking(new ArrayList<>());
        home1.setId(123L);
        home1.setName("Name");
        home1.setPrice(1);
        home1.setRented("Rented");
        home1.setType("Type");

        Booking booking = new Booking();
        booking.setCancellation(cancellation);
        booking.setReceipt(receipt);
        booking.setFinish_date(LocalDate.ofEpochDay(1L));
        booking.setId(123L);
        booking.setPrice(1);
        booking.setStatus("Status");
        booking.setType_booking("Type booking");
        booking.setHome(home1);

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        assertEquals(1, bookingService.transferBookingListToDtoList(bookingList).size());
    }

    /**
     * Method under test: {@link BookingService#getBookingById(Long)}
     */
    @Test
    void testGetBookingById() {
        assertThrows(RecordNotFoundException.class, () -> bookingService.getBookingById(123L));
    }


    @Test
    void testAddBooking2() {
        BookingInputDto bookingInputDto = new BookingInputDto();
        bookingInputDto.setStatus("Status");
        BookingDto actualAddBookingResult = bookingService.addBooking(bookingInputDto);
        assertNull(actualAddBookingResult.getFinish_date());
        assertNull(actualAddBookingResult.getHome());
        assertNull(actualAddBookingResult.getType_booking());
        assertEquals("Status", actualAddBookingResult.getStatus());
        assertNull(actualAddBookingResult.getPrice());
    }

    @Test
    void getBooking() {
        Long id = 1L;
        when(bookingRepository.findById(id)).thenReturn(Optional.of(booking2));

        Booking booking = bookingRepository.findById(id).get();
        BookingDto dto = bookingService.getBookingById(id);

        assertEquals(booking.getHome(), dto.getHome());
        assertEquals(booking.getType_booking(), dto.getType_booking());
        assertEquals(booking.getStatus(), dto.getStatus());
        assertEquals(booking.getFinish_date(), dto.getFinish_date());
    }

    @Test
    void updateBoekingThrowsExceptionTest() {
        assertThrows(RecordNotFoundException.class, () -> bookingService.updateBooking(1L, new BookingInputDto(LocalDate.of(2023, 03, 15), 1L, "status", "geboekt", home1, 1500)));
    }

    @Test
    void getBookingThrowsExceptionTest() {
        assertThrows(RecordNotFoundException.class, () -> bookingService.getBookingById(null));
    }

    @Test
    void updateBookingThrowsExceptionForBookingTest() {
        when(bookingRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(RecordNotFoundException.class, () -> bookingService.updateBooking(1L, new BookingInputDto(LocalDate.of(2023, 03, 15), 1L, "status", "geboekt", home2, 750)));
    }

    /**
     * Method under test: {@link BookingService#transferToBooking(BookingInputDto)}
     */
    @Test
    void testTransferToBooking() {
        Booking actualTransferToBookingResult = bookingService.transferToBooking(new BookingInputDto());
        assertNull(actualTransferToBookingResult.getHome());
        assertNull(actualTransferToBookingResult.getType_booking());
        assertNull(actualTransferToBookingResult.getStatus());
        assertNull(actualTransferToBookingResult.getPrice());
        assertNull(actualTransferToBookingResult.getFinish_date());
    }

    /**
     * Method under test: {@link BookingService#assignReceiptToBooking(Long, Long)}
     */
    @Test
    void testAssignReceiptToBooking() {
        assertThrows(RecordNotFoundException.class, () -> bookingService.assignReceiptToBooking(123L, 123L));
        assertThrows(RecordNotFoundException.class, () -> bookingService.assignReceiptToBooking(1L, 123L));
    }

    /**
     * Method under test: {@link BookingService#assignReceiptToBooking(Long, Long)}
     */
    @Test
    void testAssignReceiptToBooking2() {
        bookingService.assignReceiptToBooking(1L, 1L);
        assertEquals(78, bookingService.getAllBooking().size());
    }

    /**
     * Method under test: {@link BookingService#assignCancellationToBooking(Long, Long)}
     */
    @Test
    void testAssignCancellationToBooking() {
        assertThrows(RecordNotFoundException.class, () -> bookingService.assignCancellationToBooking(123L, 123L));
        assertThrows(RecordNotFoundException.class, () -> bookingService.assignCancellationToBooking(1L, 123L));
    }

    /**
     * Method under test: {@link BookingService#assignCancellationToBooking(Long, Long)}
     */
    @Test
    void testAssignCancellationToBooking2() {
        bookingService.assignCancellationToBooking(1L, 1L);
        assertEquals(78, bookingService.getAllBooking().size());
    }

    /**
     * Method under test: {@link BookingService#assignHomeToBooking(Long, Long)}
     */
    @Test
    void testAssignHomeToBooking() {
        assertThrows(RecordNotFoundException.class, () -> bookingService.assignHomeToBooking(123L, 123L));
        assertThrows(RecordNotFoundException.class, () -> bookingService.assignHomeToBooking(1L, 123L));
    }

    /**
     * Method under test: {@link BookingService#assignHomeToBooking(Long, Long)}
     */
    @Test
    void testAssignHomeToBooking2() {
        bookingService.assignHomeToBooking(1L, 1L);
        assertEquals(78, bookingService.getAllBooking().size());
    }
}

