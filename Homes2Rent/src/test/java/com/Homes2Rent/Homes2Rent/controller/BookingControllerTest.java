package com.Homes2Rent.Homes2Rent.controller;

import com.Homes2Rent.Homes2Rent.dto.BookingDto;
import com.Homes2Rent.Homes2Rent.dto.BookingInputDto;
import com.Homes2Rent.Homes2Rent.dto.HomeDto;
import com.Homes2Rent.Homes2Rent.dto.HomeInputDto;
import com.Homes2Rent.Homes2Rent.model.Booking;
import com.Homes2Rent.Homes2Rent.model.Cancellation;
import com.Homes2Rent.Homes2Rent.model.Home;
import com.Homes2Rent.Homes2Rent.model.Receipt;
import com.Homes2Rent.Homes2Rent.repository.CancellationRepository;
import com.Homes2Rent.Homes2Rent.repository.BookingRepository;
import com.Homes2Rent.Homes2Rent.repository.HomeRepository;
import com.Homes2Rent.Homes2Rent.repository.ReceiptRepository;
import com.Homes2Rent.Homes2Rent.security.JwtService;
import com.Homes2Rent.Homes2Rent.service.BookingService;
import com.Homes2Rent.Homes2Rent.service.CancellationService;
import com.Homes2Rent.Homes2Rent.service.ReceiptService;
import com.Homes2Rent.Homes2Rent.service.HomeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ContextConfiguration(classes = {BookingController.class, BookingService.class, HomeService.class,
        ReceiptService.class, CancellationService.class})
@WebMvcTest(BookingController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class BookingControllerTest {

    @MockBean
    private CancellationRepository cancellationRepository;

    @Autowired
    private BookingController bookingController;

    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private ReceiptRepository receiptRepository;

    @MockBean
    private HomeRepository homeRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private BookingService service;

    @MockBean
    JwtService jwtService;

    Home home1;
    Home home2;

    HomeInputDto homeInputDto1;
    HomeInputDto homeInputDto2;

    HomeDto homeDto1;
    HomeDto homeDto2;

    Booking booking1;
    Booking booking2;

    BookingDto bookingDto1;
    BookingDto bookingDto2;


    BookingInputDto bookingInputDto1;
    BookingInputDto bookingInputDto2;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        home1 = new Home(1L, "huis", "villa happiness", 1500, "rented");
        home2 = new Home(2L, "appartament", "cozy rooms", 750, "rented");

        homeDto1 = new HomeDto(1L, "huis", "villa happiness", 1500, "rented");
        homeDto2 = new HomeDto(2L, "appartament", "cozy rooms", 750, "rented");

        homeInputDto1 = new HomeInputDto(1L, "huis", "villa hapiness", 1500, "rented");
        homeInputDto2 = new HomeInputDto(2L, "appartament", "cozy rooms", 750, "rented");

        booking1 = new Booking(1L, LocalDate.of(2023, 03, 15), "status", "geboekt", 1500, home1);
        booking2 = new Booking(1L, LocalDate.of(2023, 03, 21), "status", "geboekt", 750, home2);

        bookingDto1 = new BookingDto(1L, LocalDate.of(2023, 03, 15), "status", "geboekt", home1, 1500);
        bookingDto2 = new BookingDto(2L, LocalDate.of(2022, 03, 21), "status", "geboekt", home2, 750);

        bookingInputDto1 = new BookingInputDto(LocalDate.of(2023, 03, 15), 1L, "status", "geboekt", home1, 1500);
        bookingInputDto2 = new BookingInputDto(LocalDate.of(2023, 03, 21), 2L, "status", "geboekt", home2, 750);
    }

    @Test
    void getAllBookingen() throws Exception {

        given(service.getAllBooking()).willReturn(List.of(bookingDto1, bookingDto2));

        mockMvc.perform(get("/booking"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type_booking").value("rented"))
                .andExpect(jsonPath("$[0].finish_date").value("2023-02-01"))
                .andExpect(jsonPath("$[0].notes").value(""))
                .andExpect(jsonPath("$[0].status").value("rented"))
                .andExpect(jsonPath("$[1].type_booking").value("vrij"))
                .andExpect(jsonPath("$[1].finish_date").value("2023-03-01"))
                .andExpect(jsonPath("$[1].notes").value(""))
                .andExpect(jsonPath("$[1].status").value("vrij"));
    }

    @Test
    void getBookingById() throws Exception {
        Long id = 1L;
        given(service.getBookingById(id)).willReturn(bookingDto1);
        mockMvc.perform(get("/booking/1"))
                .andExpect(status().isOk())
                .andDo(print())

                .andExpect(jsonPath("type_booking").value("rented"))
                .andExpect(jsonPath("finish_date").value("2023-02-01"))
                .andExpect(jsonPath("notes").value(""))
                .andExpect(jsonPath("status").value("rented"));
    }

    @Test
    void createBooking() throws Exception {
        when(service.addBooking(any((BookingInputDto.class)))).thenReturn(bookingDto1);
        mockMvc.perform(post("/booking")

                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(asJsonString(bookingInputDto1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("notes").value(""))
                .andExpect(jsonPath("status").value("rented"))
                .andExpect(jsonPath("type_booking").value("geboekt"));
    }

    /**
     * Method under test: {@link BookingController#addBooking(BookingInputDto)}
     */
    @Test
    void testAddBooking() throws Exception {
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
        when(bookingRepository.save((Booking) org.mockito.Mockito.any())).thenReturn(booking);

        Home home2 = new Home();
        home2.setBooking(new ArrayList<>());
        home2.setId(123L);
        home2.setName("Name");
        home2.setPrice(1);
        home2.setRented("Rented");
        home2.setType("Type");

        BookingInputDto bookingInputDto = new BookingInputDto();
        bookingInputDto.setFinish_date(LocalDate.ofEpochDay(1L));
        bookingInputDto.setId(123L);
        bookingInputDto.setPrice(1);
        bookingInputDto.setStatus("Status");
        bookingInputDto.setType_booking("Type booking");
        bookingInputDto.setHome(home2);
        String content = (new ObjectMapper()).writeValueAsString(bookingInputDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/booking")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(bookingController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":null,\"finish_date\":[1970,1,2],\"status\":\"Status\",\"type_booking\":\"Type booking\",\"home\":{\"id\":123"
                                        + ",\"type\":\"Type\",\"name\":\"Name\",\"price\":1,\"rented\":\"Rented\"},\"price\":1}"));
    }

    /**
     * Method under test: {@link BookingController#assignCancellationToBooking(Long, Long)}
     */
    @Test
    void testAssignCancellationToBooking() throws Exception {
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
        Optional<Booking> ofResult = Optional.of(booking);

        Home home2 = new Home();
        home2.setBooking(new ArrayList<>());
        home2.setId(123L);
        home2.setName("Name");
        home2.setPrice(1);
        home2.setRented("Rented");
        home2.setType("Type");

        Cancellation cancellation1 = new Cancellation();
        cancellation1.setFinish_date(LocalDate.ofEpochDay(1L));
        cancellation1.setId(123L);
        cancellation1.setName("Name");
        cancellation1.setPrice(1);
        cancellation1.setStatus("Status");
        cancellation1.setType_booking("Type booking");
        cancellation1.setHome(home2);

        Receipt receipt1 = new Receipt();
        receipt1.setId(123L);
        receipt1.setKlant("Klant");
        receipt1.setPrice(1);

        Home home3 = new Home();
        home3.setBooking(new ArrayList<>());
        home3.setId(123L);
        home3.setName("Name");
        home3.setPrice(1);
        home3.setRented("Rented");
        home3.setType("Type");

        Booking booking1 = new Booking();
        booking1.setCancellation(cancellation1);
        booking1.setReceipt(receipt1);
        booking1.setFinish_date(LocalDate.ofEpochDay(1L));
        booking1.setId(123L);
        booking1.setPrice(1);
        booking1.setStatus("Status");
        booking1.setType_booking("Type booking");
        booking1.setHome(home3);
        when(bookingRepository.save((Booking) org.mockito.Mockito.any())).thenReturn(booking1);
        when(bookingRepository.findById((Long) org.mockito.Mockito.any())).thenReturn(ofResult);

        Home home4 = new Home();
        home4.setBooking(new ArrayList<>());
        home4.setId(123L);
        home4.setName("Name");
        home4.setPrice(1);
        home4.setRented("Rented");
        home4.setType("Type");

        Cancellation cancellation2 = new Cancellation();
        cancellation2.setFinish_date(LocalDate.ofEpochDay(1L));
        cancellation2.setId(123L);
        cancellation2.setName("Name");
        cancellation2.setPrice(1);
        cancellation2.setStatus("Status");
        cancellation2.setType_booking("Type booking");
        cancellation2.setHome(home4);
        Optional<Cancellation> ofResult1 = Optional.of(cancellation2);
        when(cancellationRepository.findById((Long) org.mockito.Mockito.any())).thenReturn(ofResult1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/booking/booking/{id}/{cancellationId}", 123L, 123L);
        MockMvcBuilders.standaloneSetup(bookingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link BookingController#assignCancellationToBooking(Long, Long)}
     */
    @Test
    void testAssignCancellationToBooking2() throws Exception {
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
        Optional<Booking> ofResult = Optional.of(booking);

        Home home2 = new Home();
        home2.setBooking(new ArrayList<>());
        home2.setId(123L);
        home2.setName("Name");
        home2.setPrice(1);
        home2.setRented("Rented");
        home2.setType("Type");

        Cancellation cancellation1 = new Cancellation();
        cancellation1.setFinish_date(LocalDate.ofEpochDay(1L));
        cancellation1.setId(123L);
        cancellation1.setName("Name");
        cancellation1.setPrice(1);
        cancellation1.setStatus("Status");
        cancellation1.setType_booking("Type booking");
        cancellation1.setHome(home2);

        Receipt receipt1 = new Receipt();
        receipt1.setId(123L);
        receipt1.setKlant("Klant");
        receipt1.setPrice(1);

        Home home3 = new Home();
        home3.setBooking(new ArrayList<>());
        home3.setId(123L);
        home3.setName("Name");
        home3.setPrice(1);
        home3.setRented("Rented");
        home3.setType("Type");

        Booking booking1 = new Booking();
        booking1.setCancellation(cancellation1);
        booking1.setReceipt(receipt1);
        booking1.setFinish_date(LocalDate.ofEpochDay(1L));
        booking1.setId(123L);
        booking1.setPrice(1);
        booking1.setStatus("Status");
        booking1.setType_booking("Type booking");
        booking1.setHome(home3);
        when(bookingRepository.save((Booking) org.mockito.Mockito.any())).thenReturn(booking1);
        when(bookingRepository.findById((Long) org.mockito.Mockito.any())).thenReturn(ofResult);

        Home home4 = new Home();
        home4.setBooking(new ArrayList<>());
        home4.setId(123L);
        home4.setName("Name");
        home4.setPrice(1);
        home4.setRented("Rented");
        home4.setType("Type");

        Cancellation cancellation2 = new Cancellation();
        cancellation2.setFinish_date(LocalDate.ofEpochDay(1L));
        cancellation2.setId(123L);
        cancellation2.setName("Name");
        cancellation2.setPrice(1);
        cancellation2.setStatus("Status");
        cancellation2.setType_booking("Type booking");
        cancellation2.setHome(home4);
        Optional<Cancellation> ofResult1 = Optional.of(cancellation2);
        when(cancellationRepository.findById((Long) org.mockito.Mockito.any())).thenReturn(ofResult1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/booking/booking/{id}/{camcellationId}", 123L, 123L);
        MockMvcBuilders.standaloneSetup(bookingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link BookingController#assignReceiptToBooking(Long, Long)}
     */
    @Test
    void testAssignReceiptToBooking() throws Exception {
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
        Optional<Booking> ofResult = Optional.of(booking);

        Home home2 = new Home();
        home2.setBooking(new ArrayList<>());
        home2.setId(123L);
        home2.setName("Name");
        home2.setPrice(1);
        home2.setRented("Rented");
        home2.setType("Type");

        Cancellation cancellation1 = new Cancellation();
        cancellation1.setFinish_date(LocalDate.ofEpochDay(1L));
        cancellation1.setId(123L);
        cancellation1.setName("Name");
        cancellation1.setPrice(1);
        cancellation1.setStatus("Status");
        cancellation1.setType_booking("Type booking");
        cancellation1.setHome(home2);

        Receipt receipt1 = new Receipt();
        receipt1.setId(123L);
        receipt1.setKlant("Klant");
        receipt1.setPrice(1);

        Home home3 = new Home();
        home3.setBooking(new ArrayList<>());
        home3.setId(123L);
        home3.setName("Name");
        home3.setPrice(1);
        home3.setRented("Rented");
        home3.setType("Type");

        Booking booking1 = new Booking();
        booking1.setCancellation(cancellation1);
        booking1.setReceipt(receipt1);
        booking1.setFinish_date(LocalDate.ofEpochDay(1L));
        booking1.setId(123L);
        booking1.setPrice(1);
        booking1.setStatus("Status");
        booking1.setType_booking("Type booking");
        booking1.setHome(home3);
        when(bookingRepository.save((Booking) org.mockito.Mockito.any())).thenReturn(booking1);
        when(bookingRepository.findById((Long) org.mockito.Mockito.any())).thenReturn(ofResult);

        Receipt receipt2 = new Receipt();
        receipt2.setId(123L);
        receipt2.setKlant("Klant");
        receipt2.setPrice(1);
        Optional<Receipt> ofResult1 = Optional.of(receipt2);
        when(receiptRepository.findById((Long) org.mockito.Mockito.any())).thenReturn(ofResult1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/booking/{id}/receipt/{receipt_id}",
                123L, 1L);
        MockMvcBuilders.standaloneSetup(bookingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link BookingController#assignHomeToBooking(Long, Long)} ToBooking(Long, Long)}
     */
    @Test
    void testAssignHomeToBooking() throws Exception {
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
        Optional<Booking> ofResult = Optional.of(booking);

        Home home2 = new Home();
        home2.setBooking(new ArrayList<>());
        home2.setId(123L);
        home2.setName("Name");
        home2.setPrice(1);
        home2.setRented("Rented");
        home2.setType("Type");

        Cancellation cancellation1 = new Cancellation();
        cancellation1.setFinish_date(LocalDate.ofEpochDay(1L));
        cancellation1.setId(123L);
        cancellation1.setName("Name");
        cancellation1.setPrice(1);
        cancellation1.setStatus("Status");
        cancellation1.setType_booking("Type booking");
        cancellation1.setHome(home2);

        Receipt receipt1 = new Receipt();
        receipt1.setId(123L);
        receipt1.setKlant("Klant");
        receipt1.setPrice(1);

        Home home3 = new Home();
        home3.setBooking(new ArrayList<>());
        home3.setId(123L);
        home3.setName("Name");
        home3.setPrice(1);
        home3.setRented("Rented");
        home3.setType("Type");

        Booking booking1 = new Booking();
        booking1.setCancellation(cancellation1);
        booking1.setReceipt(receipt1);
        booking1.setFinish_date(LocalDate.ofEpochDay(1L));
        booking1.setId(123L);
        booking1.setPrice(1);
        booking1.setStatus("Status");
        booking1.setType_booking("Type booking");
        booking1.setHome(home3);
        when(bookingRepository.save((Booking) org.mockito.Mockito.any())).thenReturn(booking1);
        when(bookingRepository.findById((Long) org.mockito.Mockito.any())).thenReturn(ofResult);

        Home home4 = new Home();
        home4.setBooking(new ArrayList<>());
        home4.setId(123L);
        home4.setName("Name");
        home4.setPrice(1);
        home4.setRented("Rented");
        home4.setType("Type");
        Optional<Home> ofResult1 = Optional.of(home4);
        when(homeRepository.findById((Long) org.mockito.Mockito.any())).thenReturn(ofResult1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/booking/{id}/{homeId}", 123L,
                123L);
        MockMvcBuilders.standaloneSetup(bookingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link BookingController#assignHomeToBooking(Long, Long)} ToBooking(Long, Long)}
     */
    @Test
    void testAssignHomeToBooking2() throws Exception {
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
        Optional<Booking> ofResult = Optional.of(booking);

        Home home2 = new Home();
        home2.setBooking(new ArrayList<>());
        home2.setId(123L);
        home2.setName("Name");
        home2.setPrice(1);
        home2.setRented("Rented");
        home2.setType("Type");

        Cancellation cancellation1 = new Cancellation();
        cancellation1.setFinish_date(LocalDate.ofEpochDay(1L));
        cancellation1.setId(123L);
        cancellation1.setName("Name");
        cancellation1.setPrice(1);
        cancellation1.setStatus("Status");
        cancellation1.setType_booking("Type booking");
        cancellation1.setHome(home2);

        Receipt receipt1 = new Receipt();
        receipt1.setId(123L);
        receipt1.setKlant("Klant");
        receipt1.setPrice(1);

        Home home3 = new Home();
        home3.setBooking(new ArrayList<>());
        home3.setId(123L);
        home3.setName("Name");
        home3.setPrice(1);
        home3.setRented("Rented");
        home3.setType("Type");

        Booking booking1 = new Booking();
        booking1.setCancellation(cancellation1);
        booking1.setReceipt(receipt1);
        booking1.setFinish_date(LocalDate.ofEpochDay(1L));
        booking1.setId(123L);
        booking1.setPrice(1);
        booking1.setStatus("Status");
        booking1.setType_booking("Type booking");
        booking1.setHome(home3);
        when(bookingRepository.save((Booking) org.mockito.Mockito.any())).thenReturn(booking1);
        when(bookingRepository.findById((Long) org.mockito.Mockito.any())).thenReturn(ofResult);

        Home home4 = new Home();
        home4.setBooking(new ArrayList<>());
        home4.setId(123L);
        home4.setName("Name");
        home4.setPrice(1);
        home4.setRented("Rented");
        home4.setType("Type");
        Optional<Home> ofResult1 = Optional.of(home4);
        when(homeRepository.findById((Long) org.mockito.Mockito.any())).thenReturn(ofResult1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/booking/{id}/{homeId}", 123L,
                123L);
        MockMvcBuilders.standaloneSetup(bookingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link BookingController#assignHomeToBooking(Long, Long)} ToBooking(Long, Long)}
     */
    @Test
    void testAssignHomeToBoOking3() throws Exception {
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
        Optional<Booking> ofResult = Optional.of(booking);

        Home home2 = new Home();
        home2.setBooking(new ArrayList<>());
        home2.setId(123L);
        home2.setName("Name");
        home2.setPrice(1);
        home2.setRented("Rented");
        home2.setType("Type");

        Cancellation cancellation1 = new Cancellation();
        cancellation1.setFinish_date(LocalDate.ofEpochDay(1L));
        cancellation1.setId(123L);
        cancellation1.setName("Name");
        cancellation1.setPrice(1);
        cancellation1.setStatus("Status");
        cancellation1.setType_booking("Type booking");
        cancellation1.setHome(home2);

        Receipt receipt1 = new Receipt();
        receipt1.setId(123L);
        receipt1.setKlant("Klant");
        receipt1.setPrice(1);

        Home home3 = new Home();
        home3.setBooking(new ArrayList<>());
        home3.setId(123L);
        home3.setName("Name");
        home3.setPrice(1);
        home3.setRented("Rented");
        home3.setType("Type");

        Booking booking1 = new Booking();
        booking1.setCancellation(cancellation1);
        booking1.setReceipt(receipt1);
        booking1.setFinish_date(LocalDate.ofEpochDay(1L));
        booking1.setId(123L);
        booking1.setPrice(1);
        booking1.setStatus("Status");
        booking1.setType_booking("Type booking");
        booking1.setHome(home3);
        when(bookingRepository.save((Booking) org.mockito.Mockito.any())).thenReturn(booking1);
        when(bookingRepository.findById((Long) org.mockito.Mockito.any())).thenReturn(ofResult);

        Home home4 = new Home();
        home4.setBooking(new ArrayList<>());
        home4.setId(123L);
        home4.setName("Name");
        home4.setPrice(1);
        home4.setRented("Rented");
        home4.setType("Type");
        Optional<Home> ofResult1 = Optional.of(home4);
        when(homeRepository.findById((Long) org.mockito.Mockito.any())).thenReturn(ofResult1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/booking/{id}/{homeId}", 123L,
                123L);
        MockMvcBuilders.standaloneSetup(bookingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link BookingController#assignHomeToBooking(Long, Long)} ToBooking(Long, Long)}
     */
    @Test
    void testAssignHomeToBooking4() throws Exception {
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
        Optional<Booking> ofResult = Optional.of(booking);

        Home home2 = new Home();
        home2.setBooking(new ArrayList<>());
        home2.setId(123L);
        home2.setName("Name");
        home2.setPrice(1);
        home2.setRented("Rented");
        home2.setType("Type");

        Cancellation cancellation1 = new Cancellation();
        cancellation1.setFinish_date(LocalDate.ofEpochDay(1L));
        cancellation1.setId(123L);
        cancellation1.setName("Name");
        cancellation1.setPrice(1);
        cancellation1.setStatus("Status");
        cancellation1.setType_booking("Type booking");
        cancellation1.setHome(home2);

        Receipt receipt1 = new Receipt();
        receipt1.setId(123L);
        receipt1.setKlant("Klant");
        receipt1.setPrice(1);

        Home home3 = new Home();
        home3.setBooking(new ArrayList<>());
        home3.setId(123L);
        home3.setName("Name");
        home3.setPrice(1);
        home3.setRented("Rented");
        home3.setType("Type");

        Booking booking1 = new Booking();
        booking1.setCancellation(cancellation1);
        booking1.setReceipt(receipt1);
        booking1.setFinish_date(LocalDate.ofEpochDay(1L));
        booking1.setId(123L);
        booking1.setPrice(1);
        booking1.setStatus("Status");
        booking1.setType_booking("Type booking");
        booking1.setHome(home3);
        when(bookingRepository.save((Booking) org.mockito.Mockito.any())).thenReturn(booking1);
        when(bookingRepository.findById((Long) org.mockito.Mockito.any())).thenReturn(ofResult);

        Home home4 = new Home();
        home4.setBooking(new ArrayList<>());
        home4.setId(123L);
        home4.setName("Name");
        home4.setPrice(1);
        home4.setRented("Rented");
        home4.setType("Type");
        Optional<Home> ofResult1 = Optional.of(home4);
        when(homeRepository.findById((Long) org.mockito.Mockito.any())).thenReturn(ofResult1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/booking/{id}/{homeId}", 123L,
                123L);
        MockMvcBuilders.standaloneSetup(bookingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link BookingController#deleteBooking(Long)}
     */
    @Test
    void testDeleteBooking() throws Exception {
        doNothing().when(bookingRepository).deleteById((Long) org.mockito.Mockito.any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/booking/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(bookingController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link BookingController#deleteBooking(Long)}
     */
    @Test
    void testDeleteBooking2() throws Exception {
        doNothing().when(bookingRepository).deleteById((Long) org.mockito.Mockito.any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/booking/{id}", 123L);
        deleteResult.characterEncoding("Encoding");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(bookingController)
                .build()
                .perform(deleteResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link BookingController#getAllBooking()}
     */
    @Test
    void testGetAllBooking() throws Exception {
        when(bookingRepository.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/booking");
        MockMvcBuilders.standaloneSetup(bookingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link BookingController#getAllBooking()}
     */
    @Test
    void testGetAllBooking2() throws Exception {
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
        when(bookingRepository.findAll()).thenReturn(bookingList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/booking");
        MockMvcBuilders.standaloneSetup(bookingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"id\":123,\"finish_date\":[1970,1,2],\"status\":\"Status\",\"type_booking\":\"Type booking\",\"home\":{\"id\":123"
                                        + ",\"type\":\"Type\",\"name\":\"Name\",\"price\":1,\"rented\":\"Rented\"},\"price\":1}]"));
    }

    /**
     * Method under test: {@link BookingController#getBookingById(Long)}
     */
    @Test
    void testGetBookingById() throws Exception {
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
        Optional<Booking> ofResult = Optional.of(booking);
        when(bookingRepository.findById((Long) org.mockito.Mockito.any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/booking/{id}", 123L);
        MockMvcBuilders.standaloneSetup(bookingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"finish_date\":[1970,1,2],\"status\":\"Status\",\"type_booking\":\"Type booking\",\"home\":{\"id\":123"
                                        + ",\"type\":\"Type\",\"name\":\"Name\",\"price\":1,\"rented\":\"Rented\"},\"price\":1}"));
    }

    /**
     * Method under test: {@link BookingController#updateBooking(Long, BookingInputDto)}
     */
    @Test
    void testUpdateBooking() throws Exception {
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
        Optional<Booking> ofResult = Optional.of(booking);

        Home home2 = new Home();
        home2.setBooking(new ArrayList<>());
        home2.setId(123L);
        home2.setName("Name");
        home2.setPrice(1);
        home2.setRented("Rented");
        home2.setType("Type");

        Cancellation cancellation1 = new Cancellation();
        cancellation1.setFinish_date(LocalDate.ofEpochDay(1L));
        cancellation1.setId(123L);
        cancellation1.setName("Name");
        cancellation1.setPrice(1);
        cancellation1.setStatus("Status");
        cancellation1.setType_booking("Type booking");
        cancellation1.setHome(home2);

        Receipt receipt1 = new Receipt();
        receipt1.setId(123L);
        receipt1.setKlant("Klant");
        receipt1.setPrice(1);

        Home home3 = new Home();
        home3.setBooking(new ArrayList<>());
        home3.setId(123L);
        home3.setName("Name");
        home3.setPrice(1);
        home3.setRented("Rented");
        home3.setType("Type");

        Booking booking1 = new Booking();
        booking1.setCancellation(cancellation1);
        booking1.setReceipt(receipt1);
        booking1.setFinish_date(LocalDate.ofEpochDay(1L));
        booking1.setId(123L);
        booking1.setPrice(1);
        booking1.setStatus("Status");
        booking1.setType_booking("Type booking");
        booking1.setHome(home3);
        when(bookingRepository.save((Booking) org.mockito.Mockito.any())).thenReturn(booking1);
        when(bookingRepository.findById((Long) org.mockito.Mockito.any())).thenReturn(ofResult);

        Home home4 = new Home();
        home4.setBooking(new ArrayList<>());
        home4.setId(123L);
        home4.setName("Name");
        home4.setPrice(1);
        home4.setRented("Rented");
        home4.setType("Type");

        BookingInputDto bookingInputDto = new BookingInputDto();
        bookingInputDto.setFinish_date(LocalDate.ofEpochDay(1L));
        bookingInputDto.setId(123L);
        bookingInputDto.setPrice(1);
        bookingInputDto.setStatus("Status");
        bookingInputDto.setType_booking("Type booking");
        bookingInputDto.setHome(home4);
        String content = (new ObjectMapper()).writeValueAsString(bookingInputDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/booking/{id}", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(bookingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"finish_date\":[1970,1,2],\"status\":\"Status\",\"type_booking\":\"Type booking\",\"home\":{\"id\":123"
                                        + ",\"type\":\"Type\",\"name\":\"Name\",\"price\":1,\"rented\":\"Rented\"},\"price\":1}"));
    }

    /**
     * Method under test: {@link BookingController#updateBooking(Long, BookingInputDto)}
     */
    @Test
    void testUpdateBooking2() throws Exception {
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
        Optional<Booking> ofResult = Optional.of(booking);

        Home home2 = new Home();
        home2.setBooking(new ArrayList<>());
        home2.setId(123L);
        home2.setName("Name");
        home2.setPrice(1);
        home2.setRented("Rented");
        home2.setType("Type");

        Cancellation cancellation1 = new Cancellation();
        cancellation1.setFinish_date(LocalDate.ofEpochDay(1L));
        cancellation1.setId(123L);
        cancellation1.setName("Name");
        cancellation1.setPrice(1);
        cancellation1.setStatus("Status");
        cancellation1.setType_booking("Type booking");
        cancellation1.setHome(home2);

        Receipt receipt1 = new Receipt();
        receipt1.setId(123L);
        receipt1.setKlant("Klant");
        receipt1.setPrice(1);

        Home home3 = new Home();
        home3.setBooking(new ArrayList<>());
        home3.setId(123L);
        home3.setName("Name");
        home3.setPrice(1);
        home3.setRented("Rented");
        home3.setType("Type");

        Booking booking1 = new Booking();
        booking1.setCancellation(cancellation1);
        booking1.setReceipt(receipt1);
        booking1.setFinish_date(LocalDate.ofEpochDay(1L));
        booking1.setId(123L);
        booking1.setPrice(1);
        booking1.setStatus("Status");
        booking1.setType_booking("Type booking");
        booking1.setHome(home3);
        when(bookingRepository.save((Booking) org.mockito.Mockito.any())).thenReturn(booking1);
        when(bookingRepository.findById((Long) org.mockito.Mockito.any())).thenReturn(ofResult);

        Home home4 = new Home();
        home4.setBooking(new ArrayList<>());
        home4.setId(123L);
        home4.setName("Name");
        home4.setPrice(1);
        home4.setRented("Rented");
        home4.setType("Type");

        BookingInputDto bookingInputDto = new BookingInputDto();
        bookingInputDto.setFinish_date(LocalDate.ofEpochDay(1L));
        bookingInputDto.setId(123L);
        bookingInputDto.setPrice(1);
        bookingInputDto.setStatus("Status");
        bookingInputDto.setType_booking("Type booking");
        bookingInputDto.setHome(home4);
        String content = (new ObjectMapper()).writeValueAsString(bookingInputDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/booking/{id}", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(bookingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"finish_date\":[1970,1,2],\"status\":\"Status\",\"type_booking\":\"Type booking\",\"home\":{\"id\":123"
                                        + ",\"type\":\"Type\",\"name\":\"Name\",\"price\":1,\"rented\":\"Rented\"},\"price\":1}"));
    }

    @Test
    void updateBooking() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.put("/booking/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(asJsonString(bookingInputDto2)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteBooking() throws Exception {
        mockMvc.perform(delete("/booking/1"))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
