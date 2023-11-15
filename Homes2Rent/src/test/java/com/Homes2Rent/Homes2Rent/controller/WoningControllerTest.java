package com.Homes2Rent.Homes2Rent.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.Homes2Rent.Homes2Rent.dto.HomeDto;
import com.Homes2Rent.Homes2Rent.model.Home;
import com.Homes2Rent.Homes2Rent.repository.BookingRepository;
import com.Homes2Rent.Homes2Rent.repository.CancellationRepository;
import com.Homes2Rent.Homes2Rent.repository.HomeRepository;
import com.Homes2Rent.Homes2Rent.repository.ReceiptRepository;
import com.Homes2Rent.Homes2Rent.service.BookingService;
import com.Homes2Rent.Homes2Rent.service.CancellationService;
import com.Homes2Rent.Homes2Rent.service.ReceiptService;
import com.Homes2Rent.Homes2Rent.service.HomeService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {HomeController.class, HomeService.class, BookingService.class,
        ReceiptService.class, CancellationService.class})
@ExtendWith(SpringExtension.class)
class WoningControllerTest {
    @MockBean
    private CancellationRepository cancellationRepository;

    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private ReceiptRepository receiptRepository;

    @Autowired
    private HomeController homeController;

    @MockBean
    private HomeRepository homeRepository;

    /**
     * Method under test: {@link HomeController#addWoning(HomeDto)}
     */
    @Test
    void testAddHome() throws Exception {
        Home home = new Home();
        home.setBooking(new ArrayList<>());
        home.setId(123L);
        home.setName("Name");
        home.setPrice(1);
        home.setRented("Rented");
        home.setType("Type");
        when(homeRepository.save((Home) any())).thenReturn(home);

        HomeDto homeDto = new HomeDto();
        homeDto.setId(123L);
        homeDto.setName("Name");
        homeDto.setPrice(1);
        homeDto.setRented("Rented");
        homeDto.setType("Type");
        String content = (new ObjectMapper()).writeValueAsString(homeDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/home")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(homeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":123,\"type\":\"Type\",\"name\":\"Name\",\"price\":1,\"rented\":\"Rented\"}"));
    }

    /**
     * Method under test: {@link HomeController#deleteWoning(Long)}
     */
    @Test
    void testDeleteHome() throws Exception {
        doNothing().when(homeRepository).deleteById((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/home/{id}", 123L);
        MockMvcBuilders.standaloneSetup(homeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link HomeController#deleteWoning(Long)}
     */
    @Test
    void testDeleteHome2() throws Exception {
        doNothing().when(homeRepository).deleteById((Long) any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/home/{id}", 123L);
        deleteResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(homeController)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link HomeController#getAllWoningen()}
     */
    @Test
    void testGetAllHome() throws Exception {
        when(homeRepository.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/home");
        MockMvcBuilders.standaloneSetup(homeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link HomeController#getAllWoningen()}
     */
    @Test
    void testGetAllHome2() throws Exception {
        Home home = new Home();
        home.setBooking(new ArrayList<>());
        home.setId(123L);
        home.setName("?");
        home.setPrice(1);
        home.setRented("?");
        home.setType("?");

        ArrayList<Home> homeList = new ArrayList<>();
        homeList.add(home);
        when(homeRepository.findAll()).thenReturn(homeList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/home");
        MockMvcBuilders.standaloneSetup(homeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("[{\"id\":123,\"type\":\"?\",\"name\":\"?\",\"price\":1,\"rented\":\"?\"}]"));
    }

    /**
     * Method under test: {@link HomeController#getWoning(Long)}
     */
    @Test
    void testGetHome() throws Exception {
        Home home = new Home();
        home.setBooking(new ArrayList<>());
        home.setId(123L);
        home.setName("Name");
        home.setPrice(1);
        home.setRented("Rented");
        home.setType("Type");
        Optional<Home> ofResult = Optional.of(home);
        when(homeRepository.findById((Long) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/home/{id}", 123L);
        MockMvcBuilders.standaloneSetup(homeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":123,\"type\":\"Type\",\"name\":\"Name\",\"price\":1,\"rented\":\"Rented\"}"));
    }

    /**
     * Method under test: {@link HomeController#updateWoning(Long, HomeDto)}
     */
    @Test
    void testUpdateHome() throws Exception {
        Home home = new Home();
        home.setBooking(new ArrayList<>());
        home.setId(123L);
        home.setName("Name");
        home.setPrice(1);
        home.setRented("Rented");
        home.setType("Type");
        Optional<Home> ofResult = Optional.of(home);

        Home home1 = new Home();
        home1.setBooking(new ArrayList<>());
        home1.setId(123L);
        home1.setName("Name");
        home1.setPrice(1);
        home1.setRented("Rented");
        home1.setType("Type");
        when(homeRepository.save((Home) any())).thenReturn(home1);
        when(homeRepository.findById((Long) any())).thenReturn(ofResult);
        when(homeRepository.existsById((Long) any())).thenReturn(true);

        HomeDto homeDto = new HomeDto();
        homeDto.setId(123L);
        homeDto.setName("Name");
        homeDto.setPrice(1);
        homeDto.setRented("Rented");
        homeDto.setType("Type");
        String content = (new ObjectMapper()).writeValueAsString(homeDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/home/{id}", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(homeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":123,\"type\":\"Type\",\"name\":\"Name\",\"price\":1,\"rented\":\"Rented\"}"));
    }
}

