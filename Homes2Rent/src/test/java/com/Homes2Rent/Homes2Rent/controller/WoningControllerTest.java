package com.Homes2Rent.Homes2Rent.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.Homes2Rent.Homes2Rent.dto.WoningDto;
import com.Homes2Rent.Homes2Rent.model.Woning;
import com.Homes2Rent.Homes2Rent.repository.AnnuleringRepository;
import com.Homes2Rent.Homes2Rent.repository.BoekingRepository;
import com.Homes2Rent.Homes2Rent.repository.FactuurRepository;
import com.Homes2Rent.Homes2Rent.repository.WoningRepository;
import com.Homes2Rent.Homes2Rent.service.AnnuleringsService;
import com.Homes2Rent.Homes2Rent.service.BoekingService;
import com.Homes2Rent.Homes2Rent.service.FactuurService;
import com.Homes2Rent.Homes2Rent.service.WoningService;
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

@ContextConfiguration(classes = {WoningController.class, WoningService.class, BoekingService.class,
        FactuurService.class, AnnuleringsService.class})
@ExtendWith(SpringExtension.class)
class WoningControllerTest {
    @MockBean
    private AnnuleringRepository annuleringRepository;

    @MockBean
    private BoekingRepository boekingRepository;

    @MockBean
    private FactuurRepository factuurRepository;

    @Autowired
    private WoningController woningController;

    @MockBean
    private WoningRepository woningRepository;

    /**
     * Method under test: {@link WoningController#addWoning(WoningDto)}
     */
    @Test
    void testAddWoning() throws Exception {
        Woning woning = new Woning();
        woning.setBoekingen(new ArrayList<>());
        woning.setId(123L);
        woning.setName("Name");
        woning.setPrice(1);
        woning.setRented("Rented");
        woning.setType("Type");
        when(woningRepository.save((Woning) any())).thenReturn(woning);

        WoningDto woningDto = new WoningDto();
        woningDto.setId(123L);
        woningDto.setName("Name");
        woningDto.setPrice(1);
        woningDto.setRented("Rented");
        woningDto.setType("Type");
        String content = (new ObjectMapper()).writeValueAsString(woningDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/woningen")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(woningController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":123,\"type\":\"Type\",\"name\":\"Name\",\"price\":1,\"rented\":\"Rented\"}"));
    }

    /**
     * Method under test: {@link WoningController#deleteWoning(Long)}
     */
    @Test
    void testDeleteWoning() throws Exception {
        doNothing().when(woningRepository).deleteById((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/woningen/{id}", 123L);
        MockMvcBuilders.standaloneSetup(woningController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link WoningController#deleteWoning(Long)}
     */
    @Test
    void testDeleteWoning2() throws Exception {
        doNothing().when(woningRepository).deleteById((Long) any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/woningen/{id}", 123L);
        deleteResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(woningController)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link WoningController#getAllWoningen()}
     */
    @Test
    void testGetAllWoningen() throws Exception {
        when(woningRepository.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/woningen");
        MockMvcBuilders.standaloneSetup(woningController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link WoningController#getAllWoningen()}
     */
    @Test
    void testGetAllWoningen2() throws Exception {
        Woning woning = new Woning();
        woning.setBoekingen(new ArrayList<>());
        woning.setId(123L);
        woning.setName("?");
        woning.setPrice(1);
        woning.setRented("?");
        woning.setType("?");

        ArrayList<Woning> woningList = new ArrayList<>();
        woningList.add(woning);
        when(woningRepository.findAll()).thenReturn(woningList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/woningen");
        MockMvcBuilders.standaloneSetup(woningController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("[{\"id\":123,\"type\":\"?\",\"name\":\"?\",\"price\":1,\"rented\":\"?\"}]"));
    }

    /**
     * Method under test: {@link WoningController#getWoning(Long)}
     */
    @Test
    void testGetWoning() throws Exception {
        Woning woning = new Woning();
        woning.setBoekingen(new ArrayList<>());
        woning.setId(123L);
        woning.setName("Name");
        woning.setPrice(1);
        woning.setRented("Rented");
        woning.setType("Type");
        Optional<Woning> ofResult = Optional.of(woning);
        when(woningRepository.findById((Long) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/woningen/{id}", 123L);
        MockMvcBuilders.standaloneSetup(woningController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":123,\"type\":\"Type\",\"name\":\"Name\",\"price\":1,\"rented\":\"Rented\"}"));
    }

    /**
     * Method under test: {@link WoningController#updateWoning(Long, WoningDto)}
     */
    @Test
    void testUpdateWoning() throws Exception {
        Woning woning = new Woning();
        woning.setBoekingen(new ArrayList<>());
        woning.setId(123L);
        woning.setName("Name");
        woning.setPrice(1);
        woning.setRented("Rented");
        woning.setType("Type");
        Optional<Woning> ofResult = Optional.of(woning);

        Woning woning1 = new Woning();
        woning1.setBoekingen(new ArrayList<>());
        woning1.setId(123L);
        woning1.setName("Name");
        woning1.setPrice(1);
        woning1.setRented("Rented");
        woning1.setType("Type");
        when(woningRepository.save((Woning) any())).thenReturn(woning1);
        when(woningRepository.findById((Long) any())).thenReturn(ofResult);
        when(woningRepository.existsById((Long) any())).thenReturn(true);

        WoningDto woningDto = new WoningDto();
        woningDto.setId(123L);
        woningDto.setName("Name");
        woningDto.setPrice(1);
        woningDto.setRented("Rented");
        woningDto.setType("Type");
        String content = (new ObjectMapper()).writeValueAsString(woningDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/woningen/{id}", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(woningController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":123,\"type\":\"Type\",\"name\":\"Name\",\"price\":1,\"rented\":\"Rented\"}"));
    }
}

