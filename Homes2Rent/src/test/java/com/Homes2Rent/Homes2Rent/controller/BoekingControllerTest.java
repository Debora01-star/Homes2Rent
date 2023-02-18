package com.Homes2Rent.Homes2Rent.controller;

import com.Homes2Rent.Homes2Rent.dto.BoekingDto;
import com.Homes2Rent.Homes2Rent.dto.BoekingInputDto;
import com.Homes2Rent.Homes2Rent.dto.WoningDto;
import com.Homes2Rent.Homes2Rent.dto.WoningInputDto;
import com.Homes2Rent.Homes2Rent.model.Boeking;
import com.Homes2Rent.Homes2Rent.model.Woning;
import com.Homes2Rent.Homes2Rent.security.JwtService;
import com.Homes2Rent.Homes2Rent.service.BoekingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.Id;
import java.time.LocalDate;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


    @WebMvcTest(BoekingController.class)
    @ExtendWith(MockitoExtension.class)
    @AutoConfigureMockMvc(addFilters = false)

class BoekingControllerTest {

        @Autowired
        private WebApplicationContext webApplicationContext;

        private MockMvc mockMvc;

        @MockBean
        private BoekingService service;

        @MockBean
        JwtService jwtService;

        Woning woning1;
        Woning woning2;

        WoningInputDto woningInputDto1;
        WoningInputDto woningInputDto2;

        WoningDto woningDto1;
        WoningDto woningDto2;

        Boeking boeking1;
        Boeking boeking2;

        BoekingDto boekingDto1;
        BoekingDto boekingDto2;


        BoekingInputDto boekingInputDto1;
        BoekingInputDto boekingInputDto2;

        @BeforeEach
        public void setUp() {
            mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

            woning1 = new Woning(1L, "huis", "villa happiness", 1500, "rented");
            woning2 = new Woning(2L, "appartament", "cozy rooms", 750, "rented");

            woningDto1 = new WoningDto(1L, "huis", "villa happiness", 1500, "rented");
            woningDto2 = new WoningDto(2L, "appartament", "cozy rooms", 750, "rented");

            woningInputDto1 = new WoningInputDto(1L, "huis","villa hapiness", 1500, "rented");
            woningInputDto2 = new WoningInputDto(2L,"appartament","cozy rooms",  750,  "rented");

            boeking1 = new Boeking(1L, LocalDate.of(2022,03,15), "status", "geboekt",1500, woning1);
            boeking2 = new Boeking(1L, LocalDate.of(2022,03,21), "status", "geboekt",750, woning2);

            boekingDto1 = new BoekingDto(1L, LocalDate.of(2022, 03, 15), "status", "geboekt", woning1, 1500);
            boekingDto2 = new BoekingDto(2L, LocalDate.of(2022, 03, 21), "status", "geboekt", woning2, 750);

            boekingInputDto1 = new BoekingInputDto(LocalDate.of(2022,03,15), 1L,  "status", "geboekt", woning1, 1500);
            boekingInputDto2 = new BoekingInputDto(LocalDate.of(2022,03,21), 2L,  "status", "geboekt", woning2, 750);
        }

        @Test
        void getAllBoekingen() throws Exception {

            given(service.getAllBoekingen()).willReturn(List.of(boekingDto1,boekingDto2));

            mockMvc.perform(get("/boekingen"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].type_boeking").value("rented"))
                    .andExpect(jsonPath("$[0].finish_date").value("2022-02-01"))
                    .andExpect(jsonPath("$[0].notes").value(""))
                    .andExpect(jsonPath("$[0].status").value("rented"))
                    .andExpect(jsonPath("$[1].type_boeking").value("vrij"))
                    .andExpect(jsonPath("$[1].finish_date").value("2022-03-01"))
                    .andExpect(jsonPath("$[1].notes").value(""))
                    .andExpect(jsonPath("$[1].status").value("vrij"));
        }

        @Test
        void getBoekingById() throws Exception {
            Long id = 1L;
            given(service.getBoekingById(id)).willReturn(boekingDto1);
            mockMvc.perform(get("/boekingen/1"))
                    .andExpect(status().isOk())
                    .andDo(print())

                    .andExpect(jsonPath("type_boeking").value("rented"))
                    .andExpect(jsonPath("finish_date").value("2022-02-01"))
                    .andExpect(jsonPath("notes").value(""))
                    .andExpect(jsonPath("status").value("rented"));
        }

        @Test
        void createBoeking() throws Exception {
            when(service.addBoeking(any((BoekingInputDto.class)))).thenReturn(boekingDto1);
            mockMvc.perform(post("/boekingen")

                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("utf-8")
                            .content(asJsonString(boekingInputDto1)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("notes").value(""))
                    .andExpect(jsonPath("status").value("rented"))
                    .andExpect(jsonPath("type_boeking").value("geboekt"));
        }

        @Test
        void updateBoeking() throws Exception {

            mockMvc.perform(MockMvcRequestBuilders.put("/boekingen/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("utf-8")
                            .content(asJsonString(boekingInputDto2)))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        void deleteBoeking() throws Exception {
            mockMvc.perform(delete("/boekingen/1"))
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
