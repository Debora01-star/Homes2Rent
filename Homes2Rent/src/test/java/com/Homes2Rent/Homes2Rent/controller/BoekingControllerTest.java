package com.Homes2Rent.Homes2Rent.controller;

import com.Homes2Rent.Homes2Rent.dto.BoekingDto;
import com.Homes2Rent.Homes2Rent.dto.BoekingInputDto;
import com.Homes2Rent.Homes2Rent.dto.WoningDto;
import com.Homes2Rent.Homes2Rent.dto.WoningInputDto;
import com.Homes2Rent.Homes2Rent.model.Annulering;
import com.Homes2Rent.Homes2Rent.model.Boeking;
import com.Homes2Rent.Homes2Rent.model.Factuur;
import com.Homes2Rent.Homes2Rent.model.Woning;
import com.Homes2Rent.Homes2Rent.repository.AnnuleringRepository;
import com.Homes2Rent.Homes2Rent.repository.BoekingRepository;
import com.Homes2Rent.Homes2Rent.repository.FactuurRepository;
import com.Homes2Rent.Homes2Rent.repository.WoningRepository;
import com.Homes2Rent.Homes2Rent.security.JwtService;
import com.Homes2Rent.Homes2Rent.service.AnnuleringsService;
import com.Homes2Rent.Homes2Rent.service.BoekingService;
import com.Homes2Rent.Homes2Rent.service.FactuurService;
import com.Homes2Rent.Homes2Rent.service.WoningService;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.Id;
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


@ContextConfiguration(classes = {BoekingController.class, BoekingService.class, WoningService.class,
        FactuurService.class, AnnuleringsService.class})
@WebMvcTest(BoekingController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class BoekingControllerTest {

    @MockBean
    private AnnuleringRepository annuleringRepository;

    @Autowired
    private BoekingController boekingController;

    @MockBean
    private BoekingRepository boekingRepository;

    @MockBean
    private FactuurRepository factuurRepository;

    @MockBean
    private WoningRepository woningRepository;

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

        woningInputDto1 = new WoningInputDto(1L, "huis", "villa hapiness", 1500, "rented");
        woningInputDto2 = new WoningInputDto(2L, "appartament", "cozy rooms", 750, "rented");

        boeking1 = new Boeking(1L, LocalDate.of(2023, 03, 15), "status", "geboekt", 1500, woning1);
        boeking2 = new Boeking(1L, LocalDate.of(2023, 03, 21), "status", "geboekt", 750, woning2);

        boekingDto1 = new BoekingDto(1L, LocalDate.of(2023, 03, 15), "status", "geboekt", woning1, 1500);
        boekingDto2 = new BoekingDto(2L, LocalDate.of(2022, 03, 21), "status", "geboekt", woning2, 750);

        boekingInputDto1 = new BoekingInputDto(LocalDate.of(2023, 03, 15), 1L, "status", "geboekt", woning1, 1500);
        boekingInputDto2 = new BoekingInputDto(LocalDate.of(2023, 03, 21), 2L, "status", "geboekt", woning2, 750);
    }

    @Test
    void getAllBoekingen() throws Exception {

        given(service.getAllBoekingen()).willReturn(List.of(boekingDto1, boekingDto2));

        mockMvc.perform(get("/boekingen"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type_boeking").value("rented"))
                .andExpect(jsonPath("$[0].finish_date").value("2023-02-01"))
                .andExpect(jsonPath("$[0].notes").value(""))
                .andExpect(jsonPath("$[0].status").value("rented"))
                .andExpect(jsonPath("$[1].type_boeking").value("vrij"))
                .andExpect(jsonPath("$[1].finish_date").value("2023-03-01"))
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
                .andExpect(jsonPath("finish_date").value("2023-02-01"))
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

    /**
     * Method under test: {@link BoekingController#addBoeking(BoekingInputDto)}
     */
    @Test
    void testAddBoeking() throws Exception {
        Woning woning = new Woning();
        woning.setBoekingen(new ArrayList<>());
        woning.setId(123L);
        woning.setName("Name");
        woning.setPrice(1);
        woning.setRented("Rented");
        woning.setType("Type");

        Annulering annulering = new Annulering();
        annulering.setFinish_date(LocalDate.ofEpochDay(1L));
        annulering.setId(123L);
        annulering.setName("Name");
        annulering.setPrice(1);
        annulering.setStatus("Status");
        annulering.setType_boeking("Type boeking");
        annulering.setWoning(woning);

        Factuur factuur = new Factuur();
        factuur.setId(123L);
        factuur.setKlant("Klant");
        factuur.setPrice(1);

        Woning woning1 = new Woning();
        woning1.setBoekingen(new ArrayList<>());
        woning1.setId(123L);
        woning1.setName("Name");
        woning1.setPrice(1);
        woning1.setRented("Rented");
        woning1.setType("Type");

        Boeking boeking = new Boeking();
        boeking.setAnnulering(annulering);
        boeking.setFactuur(factuur);
        boeking.setFinish_date(LocalDate.ofEpochDay(1L));
        boeking.setId(123L);
        boeking.setPrice(1);
        boeking.setStatus("Status");
        boeking.setType_boeking("Type boeking");
        boeking.setWoning(woning1);
        when(boekingRepository.save((Boeking) org.mockito.Mockito.any())).thenReturn(boeking);

        Woning woning2 = new Woning();
        woning2.setBoekingen(new ArrayList<>());
        woning2.setId(123L);
        woning2.setName("Name");
        woning2.setPrice(1);
        woning2.setRented("Rented");
        woning2.setType("Type");

        BoekingInputDto boekingInputDto = new BoekingInputDto();
        boekingInputDto.setFinish_date(LocalDate.ofEpochDay(1L));
        boekingInputDto.setId(123L);
        boekingInputDto.setPrice(1);
        boekingInputDto.setStatus("Status");
        boekingInputDto.setType_boeking("Type boeking");
        boekingInputDto.setWoning(woning2);
        String content = (new ObjectMapper()).writeValueAsString(boekingInputDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/boekingen")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(boekingController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":null,\"finish_date\":[1970,1,2],\"status\":\"Status\",\"type_boeking\":\"Type boeking\",\"woning\":{\"id\":123"
                                        + ",\"type\":\"Type\",\"name\":\"Name\",\"price\":1,\"rented\":\"Rented\"},\"price\":1}"));
    }

    /**
     * Method under test: {@link BoekingController#assignAnnuleringToBoeking(Long, Long)}
     */
    @Test
    void testAssignAnnuleringToBoeking() throws Exception {
        Woning woning = new Woning();
        woning.setBoekingen(new ArrayList<>());
        woning.setId(123L);
        woning.setName("Name");
        woning.setPrice(1);
        woning.setRented("Rented");
        woning.setType("Type");

        Annulering annulering = new Annulering();
        annulering.setFinish_date(LocalDate.ofEpochDay(1L));
        annulering.setId(123L);
        annulering.setName("Name");
        annulering.setPrice(1);
        annulering.setStatus("Status");
        annulering.setType_boeking("Type boeking");
        annulering.setWoning(woning);

        Factuur factuur = new Factuur();
        factuur.setId(123L);
        factuur.setKlant("Klant");
        factuur.setPrice(1);

        Woning woning1 = new Woning();
        woning1.setBoekingen(new ArrayList<>());
        woning1.setId(123L);
        woning1.setName("Name");
        woning1.setPrice(1);
        woning1.setRented("Rented");
        woning1.setType("Type");

        Boeking boeking = new Boeking();
        boeking.setAnnulering(annulering);
        boeking.setFactuur(factuur);
        boeking.setFinish_date(LocalDate.ofEpochDay(1L));
        boeking.setId(123L);
        boeking.setPrice(1);
        boeking.setStatus("Status");
        boeking.setType_boeking("Type boeking");
        boeking.setWoning(woning1);
        Optional<Boeking> ofResult = Optional.of(boeking);

        Woning woning2 = new Woning();
        woning2.setBoekingen(new ArrayList<>());
        woning2.setId(123L);
        woning2.setName("Name");
        woning2.setPrice(1);
        woning2.setRented("Rented");
        woning2.setType("Type");

        Annulering annulering1 = new Annulering();
        annulering1.setFinish_date(LocalDate.ofEpochDay(1L));
        annulering1.setId(123L);
        annulering1.setName("Name");
        annulering1.setPrice(1);
        annulering1.setStatus("Status");
        annulering1.setType_boeking("Type boeking");
        annulering1.setWoning(woning2);

        Factuur factuur1 = new Factuur();
        factuur1.setId(123L);
        factuur1.setKlant("Klant");
        factuur1.setPrice(1);

        Woning woning3 = new Woning();
        woning3.setBoekingen(new ArrayList<>());
        woning3.setId(123L);
        woning3.setName("Name");
        woning3.setPrice(1);
        woning3.setRented("Rented");
        woning3.setType("Type");

        Boeking boeking1 = new Boeking();
        boeking1.setAnnulering(annulering1);
        boeking1.setFactuur(factuur1);
        boeking1.setFinish_date(LocalDate.ofEpochDay(1L));
        boeking1.setId(123L);
        boeking1.setPrice(1);
        boeking1.setStatus("Status");
        boeking1.setType_boeking("Type boeking");
        boeking1.setWoning(woning3);
        when(boekingRepository.save((Boeking) org.mockito.Mockito.any())).thenReturn(boeking1);
        when(boekingRepository.findById((Long) org.mockito.Mockito.any())).thenReturn(ofResult);

        Woning woning4 = new Woning();
        woning4.setBoekingen(new ArrayList<>());
        woning4.setId(123L);
        woning4.setName("Name");
        woning4.setPrice(1);
        woning4.setRented("Rented");
        woning4.setType("Type");

        Annulering annulering2 = new Annulering();
        annulering2.setFinish_date(LocalDate.ofEpochDay(1L));
        annulering2.setId(123L);
        annulering2.setName("Name");
        annulering2.setPrice(1);
        annulering2.setStatus("Status");
        annulering2.setType_boeking("Type boeking");
        annulering2.setWoning(woning4);
        Optional<Annulering> ofResult1 = Optional.of(annulering2);
        when(annuleringRepository.findById((Long) org.mockito.Mockito.any())).thenReturn(ofResult1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/boekingen/boekingen/{id}/{annuleringId}", 123L, 123L);
        MockMvcBuilders.standaloneSetup(boekingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link BoekingController#assignAnnuleringToBoeking(Long, Long)}
     */
    @Test
    void testAssignAnnuleringToBoeking2() throws Exception {
        Woning woning = new Woning();
        woning.setBoekingen(new ArrayList<>());
        woning.setId(123L);
        woning.setName("Name");
        woning.setPrice(1);
        woning.setRented("Rented");
        woning.setType("Type");

        Annulering annulering = new Annulering();
        annulering.setFinish_date(LocalDate.ofEpochDay(1L));
        annulering.setId(123L);
        annulering.setName("Name");
        annulering.setPrice(1);
        annulering.setStatus("Status");
        annulering.setType_boeking("Type boeking");
        annulering.setWoning(woning);

        Factuur factuur = new Factuur();
        factuur.setId(123L);
        factuur.setKlant("Klant");
        factuur.setPrice(1);

        Woning woning1 = new Woning();
        woning1.setBoekingen(new ArrayList<>());
        woning1.setId(123L);
        woning1.setName("Name");
        woning1.setPrice(1);
        woning1.setRented("Rented");
        woning1.setType("Type");

        Boeking boeking = new Boeking();
        boeking.setAnnulering(annulering);
        boeking.setFactuur(factuur);
        boeking.setFinish_date(LocalDate.ofEpochDay(1L));
        boeking.setId(123L);
        boeking.setPrice(1);
        boeking.setStatus("Status");
        boeking.setType_boeking("Type boeking");
        boeking.setWoning(woning1);
        Optional<Boeking> ofResult = Optional.of(boeking);

        Woning woning2 = new Woning();
        woning2.setBoekingen(new ArrayList<>());
        woning2.setId(123L);
        woning2.setName("Name");
        woning2.setPrice(1);
        woning2.setRented("Rented");
        woning2.setType("Type");

        Annulering annulering1 = new Annulering();
        annulering1.setFinish_date(LocalDate.ofEpochDay(1L));
        annulering1.setId(123L);
        annulering1.setName("Name");
        annulering1.setPrice(1);
        annulering1.setStatus("Status");
        annulering1.setType_boeking("Type boeking");
        annulering1.setWoning(woning2);

        Factuur factuur1 = new Factuur();
        factuur1.setId(123L);
        factuur1.setKlant("Klant");
        factuur1.setPrice(1);

        Woning woning3 = new Woning();
        woning3.setBoekingen(new ArrayList<>());
        woning3.setId(123L);
        woning3.setName("Name");
        woning3.setPrice(1);
        woning3.setRented("Rented");
        woning3.setType("Type");

        Boeking boeking1 = new Boeking();
        boeking1.setAnnulering(annulering1);
        boeking1.setFactuur(factuur1);
        boeking1.setFinish_date(LocalDate.ofEpochDay(1L));
        boeking1.setId(123L);
        boeking1.setPrice(1);
        boeking1.setStatus("Status");
        boeking1.setType_boeking("Type boeking");
        boeking1.setWoning(woning3);
        when(boekingRepository.save((Boeking) org.mockito.Mockito.any())).thenReturn(boeking1);
        when(boekingRepository.findById((Long) org.mockito.Mockito.any())).thenReturn(ofResult);

        Woning woning4 = new Woning();
        woning4.setBoekingen(new ArrayList<>());
        woning4.setId(123L);
        woning4.setName("Name");
        woning4.setPrice(1);
        woning4.setRented("Rented");
        woning4.setType("Type");

        Annulering annulering2 = new Annulering();
        annulering2.setFinish_date(LocalDate.ofEpochDay(1L));
        annulering2.setId(123L);
        annulering2.setName("Name");
        annulering2.setPrice(1);
        annulering2.setStatus("Status");
        annulering2.setType_boeking("Type boeking");
        annulering2.setWoning(woning4);
        Optional<Annulering> ofResult1 = Optional.of(annulering2);
        when(annuleringRepository.findById((Long) org.mockito.Mockito.any())).thenReturn(ofResult1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/boekingen/boekingen/{id}/{annuleringId}", 123L, 123L);
        MockMvcBuilders.standaloneSetup(boekingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link BoekingController#assignFactuurToBoeking(Long, Long)}
     */
    @Test
    void testAssignFactuurToBoeking() throws Exception {
        Woning woning = new Woning();
        woning.setBoekingen(new ArrayList<>());
        woning.setId(123L);
        woning.setName("Name");
        woning.setPrice(1);
        woning.setRented("Rented");
        woning.setType("Type");

        Annulering annulering = new Annulering();
        annulering.setFinish_date(LocalDate.ofEpochDay(1L));
        annulering.setId(123L);
        annulering.setName("Name");
        annulering.setPrice(1);
        annulering.setStatus("Status");
        annulering.setType_boeking("Type boeking");
        annulering.setWoning(woning);

        Factuur factuur = new Factuur();
        factuur.setId(123L);
        factuur.setKlant("Klant");
        factuur.setPrice(1);

        Woning woning1 = new Woning();
        woning1.setBoekingen(new ArrayList<>());
        woning1.setId(123L);
        woning1.setName("Name");
        woning1.setPrice(1);
        woning1.setRented("Rented");
        woning1.setType("Type");

        Boeking boeking = new Boeking();
        boeking.setAnnulering(annulering);
        boeking.setFactuur(factuur);
        boeking.setFinish_date(LocalDate.ofEpochDay(1L));
        boeking.setId(123L);
        boeking.setPrice(1);
        boeking.setStatus("Status");
        boeking.setType_boeking("Type boeking");
        boeking.setWoning(woning1);
        Optional<Boeking> ofResult = Optional.of(boeking);

        Woning woning2 = new Woning();
        woning2.setBoekingen(new ArrayList<>());
        woning2.setId(123L);
        woning2.setName("Name");
        woning2.setPrice(1);
        woning2.setRented("Rented");
        woning2.setType("Type");

        Annulering annulering1 = new Annulering();
        annulering1.setFinish_date(LocalDate.ofEpochDay(1L));
        annulering1.setId(123L);
        annulering1.setName("Name");
        annulering1.setPrice(1);
        annulering1.setStatus("Status");
        annulering1.setType_boeking("Type boeking");
        annulering1.setWoning(woning2);

        Factuur factuur1 = new Factuur();
        factuur1.setId(123L);
        factuur1.setKlant("Klant");
        factuur1.setPrice(1);

        Woning woning3 = new Woning();
        woning3.setBoekingen(new ArrayList<>());
        woning3.setId(123L);
        woning3.setName("Name");
        woning3.setPrice(1);
        woning3.setRented("Rented");
        woning3.setType("Type");

        Boeking boeking1 = new Boeking();
        boeking1.setAnnulering(annulering1);
        boeking1.setFactuur(factuur1);
        boeking1.setFinish_date(LocalDate.ofEpochDay(1L));
        boeking1.setId(123L);
        boeking1.setPrice(1);
        boeking1.setStatus("Status");
        boeking1.setType_boeking("Type boeking");
        boeking1.setWoning(woning3);
        when(boekingRepository.save((Boeking) org.mockito.Mockito.any())).thenReturn(boeking1);
        when(boekingRepository.findById((Long) org.mockito.Mockito.any())).thenReturn(ofResult);

        Factuur factuur2 = new Factuur();
        factuur2.setId(123L);
        factuur2.setKlant("Klant");
        factuur2.setPrice(1);
        Optional<Factuur> ofResult1 = Optional.of(factuur2);
        when(factuurRepository.findById((Long) org.mockito.Mockito.any())).thenReturn(ofResult1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/boekingen/{id}/factuur/{factuur_id}",
                123L, 1L);
        MockMvcBuilders.standaloneSetup(boekingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link BoekingController#assignWoningToBoeking(Long, Long)}
     */
    @Test
    void testAssignWoningToBoeking() throws Exception {
        Woning woning = new Woning();
        woning.setBoekingen(new ArrayList<>());
        woning.setId(123L);
        woning.setName("Name");
        woning.setPrice(1);
        woning.setRented("Rented");
        woning.setType("Type");

        Annulering annulering = new Annulering();
        annulering.setFinish_date(LocalDate.ofEpochDay(1L));
        annulering.setId(123L);
        annulering.setName("Name");
        annulering.setPrice(1);
        annulering.setStatus("Status");
        annulering.setType_boeking("Type boeking");
        annulering.setWoning(woning);

        Factuur factuur = new Factuur();
        factuur.setId(123L);
        factuur.setKlant("Klant");
        factuur.setPrice(1);

        Woning woning1 = new Woning();
        woning1.setBoekingen(new ArrayList<>());
        woning1.setId(123L);
        woning1.setName("Name");
        woning1.setPrice(1);
        woning1.setRented("Rented");
        woning1.setType("Type");

        Boeking boeking = new Boeking();
        boeking.setAnnulering(annulering);
        boeking.setFactuur(factuur);
        boeking.setFinish_date(LocalDate.ofEpochDay(1L));
        boeking.setId(123L);
        boeking.setPrice(1);
        boeking.setStatus("Status");
        boeking.setType_boeking("Type boeking");
        boeking.setWoning(woning1);
        Optional<Boeking> ofResult = Optional.of(boeking);

        Woning woning2 = new Woning();
        woning2.setBoekingen(new ArrayList<>());
        woning2.setId(123L);
        woning2.setName("Name");
        woning2.setPrice(1);
        woning2.setRented("Rented");
        woning2.setType("Type");

        Annulering annulering1 = new Annulering();
        annulering1.setFinish_date(LocalDate.ofEpochDay(1L));
        annulering1.setId(123L);
        annulering1.setName("Name");
        annulering1.setPrice(1);
        annulering1.setStatus("Status");
        annulering1.setType_boeking("Type boeking");
        annulering1.setWoning(woning2);

        Factuur factuur1 = new Factuur();
        factuur1.setId(123L);
        factuur1.setKlant("Klant");
        factuur1.setPrice(1);

        Woning woning3 = new Woning();
        woning3.setBoekingen(new ArrayList<>());
        woning3.setId(123L);
        woning3.setName("Name");
        woning3.setPrice(1);
        woning3.setRented("Rented");
        woning3.setType("Type");

        Boeking boeking1 = new Boeking();
        boeking1.setAnnulering(annulering1);
        boeking1.setFactuur(factuur1);
        boeking1.setFinish_date(LocalDate.ofEpochDay(1L));
        boeking1.setId(123L);
        boeking1.setPrice(1);
        boeking1.setStatus("Status");
        boeking1.setType_boeking("Type boeking");
        boeking1.setWoning(woning3);
        when(boekingRepository.save((Boeking) org.mockito.Mockito.any())).thenReturn(boeking1);
        when(boekingRepository.findById((Long) org.mockito.Mockito.any())).thenReturn(ofResult);

        Woning woning4 = new Woning();
        woning4.setBoekingen(new ArrayList<>());
        woning4.setId(123L);
        woning4.setName("Name");
        woning4.setPrice(1);
        woning4.setRented("Rented");
        woning4.setType("Type");
        Optional<Woning> ofResult1 = Optional.of(woning4);
        when(woningRepository.findById((Long) org.mockito.Mockito.any())).thenReturn(ofResult1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/boekingen/{id}/{woningId}", 123L,
                123L);
        MockMvcBuilders.standaloneSetup(boekingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link BoekingController#assignWoningToBoeking(Long, Long)}
     */
    @Test
    void testAssignWoningToBoeking2() throws Exception {
        Woning woning = new Woning();
        woning.setBoekingen(new ArrayList<>());
        woning.setId(123L);
        woning.setName("Name");
        woning.setPrice(1);
        woning.setRented("Rented");
        woning.setType("Type");

        Annulering annulering = new Annulering();
        annulering.setFinish_date(LocalDate.ofEpochDay(1L));
        annulering.setId(123L);
        annulering.setName("Name");
        annulering.setPrice(1);
        annulering.setStatus("Status");
        annulering.setType_boeking("Type boeking");
        annulering.setWoning(woning);

        Factuur factuur = new Factuur();
        factuur.setId(123L);
        factuur.setKlant("Klant");
        factuur.setPrice(1);

        Woning woning1 = new Woning();
        woning1.setBoekingen(new ArrayList<>());
        woning1.setId(123L);
        woning1.setName("Name");
        woning1.setPrice(1);
        woning1.setRented("Rented");
        woning1.setType("Type");

        Boeking boeking = new Boeking();
        boeking.setAnnulering(annulering);
        boeking.setFactuur(factuur);
        boeking.setFinish_date(LocalDate.ofEpochDay(1L));
        boeking.setId(123L);
        boeking.setPrice(1);
        boeking.setStatus("Status");
        boeking.setType_boeking("Type boeking");
        boeking.setWoning(woning1);
        Optional<Boeking> ofResult = Optional.of(boeking);

        Woning woning2 = new Woning();
        woning2.setBoekingen(new ArrayList<>());
        woning2.setId(123L);
        woning2.setName("Name");
        woning2.setPrice(1);
        woning2.setRented("Rented");
        woning2.setType("Type");

        Annulering annulering1 = new Annulering();
        annulering1.setFinish_date(LocalDate.ofEpochDay(1L));
        annulering1.setId(123L);
        annulering1.setName("Name");
        annulering1.setPrice(1);
        annulering1.setStatus("Status");
        annulering1.setType_boeking("Type boeking");
        annulering1.setWoning(woning2);

        Factuur factuur1 = new Factuur();
        factuur1.setId(123L);
        factuur1.setKlant("Klant");
        factuur1.setPrice(1);

        Woning woning3 = new Woning();
        woning3.setBoekingen(new ArrayList<>());
        woning3.setId(123L);
        woning3.setName("Name");
        woning3.setPrice(1);
        woning3.setRented("Rented");
        woning3.setType("Type");

        Boeking boeking1 = new Boeking();
        boeking1.setAnnulering(annulering1);
        boeking1.setFactuur(factuur1);
        boeking1.setFinish_date(LocalDate.ofEpochDay(1L));
        boeking1.setId(123L);
        boeking1.setPrice(1);
        boeking1.setStatus("Status");
        boeking1.setType_boeking("Type boeking");
        boeking1.setWoning(woning3);
        when(boekingRepository.save((Boeking) org.mockito.Mockito.any())).thenReturn(boeking1);
        when(boekingRepository.findById((Long) org.mockito.Mockito.any())).thenReturn(ofResult);

        Woning woning4 = new Woning();
        woning4.setBoekingen(new ArrayList<>());
        woning4.setId(123L);
        woning4.setName("Name");
        woning4.setPrice(1);
        woning4.setRented("Rented");
        woning4.setType("Type");
        Optional<Woning> ofResult1 = Optional.of(woning4);
        when(woningRepository.findById((Long) org.mockito.Mockito.any())).thenReturn(ofResult1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/boekingen/{id}/{woningId}", 123L,
                123L);
        MockMvcBuilders.standaloneSetup(boekingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link BoekingController#assignWoningToBoeking(Long, Long)}
     */
    @Test
    void testAssignWoningToBoeking3() throws Exception {
        Woning woning = new Woning();
        woning.setBoekingen(new ArrayList<>());
        woning.setId(123L);
        woning.setName("Name");
        woning.setPrice(1);
        woning.setRented("Rented");
        woning.setType("Type");

        Annulering annulering = new Annulering();
        annulering.setFinish_date(LocalDate.ofEpochDay(1L));
        annulering.setId(123L);
        annulering.setName("Name");
        annulering.setPrice(1);
        annulering.setStatus("Status");
        annulering.setType_boeking("Type boeking");
        annulering.setWoning(woning);

        Factuur factuur = new Factuur();
        factuur.setId(123L);
        factuur.setKlant("Klant");
        factuur.setPrice(1);

        Woning woning1 = new Woning();
        woning1.setBoekingen(new ArrayList<>());
        woning1.setId(123L);
        woning1.setName("Name");
        woning1.setPrice(1);
        woning1.setRented("Rented");
        woning1.setType("Type");

        Boeking boeking = new Boeking();
        boeking.setAnnulering(annulering);
        boeking.setFactuur(factuur);
        boeking.setFinish_date(LocalDate.ofEpochDay(1L));
        boeking.setId(123L);
        boeking.setPrice(1);
        boeking.setStatus("Status");
        boeking.setType_boeking("Type boeking");
        boeking.setWoning(woning1);
        Optional<Boeking> ofResult = Optional.of(boeking);

        Woning woning2 = new Woning();
        woning2.setBoekingen(new ArrayList<>());
        woning2.setId(123L);
        woning2.setName("Name");
        woning2.setPrice(1);
        woning2.setRented("Rented");
        woning2.setType("Type");

        Annulering annulering1 = new Annulering();
        annulering1.setFinish_date(LocalDate.ofEpochDay(1L));
        annulering1.setId(123L);
        annulering1.setName("Name");
        annulering1.setPrice(1);
        annulering1.setStatus("Status");
        annulering1.setType_boeking("Type boeking");
        annulering1.setWoning(woning2);

        Factuur factuur1 = new Factuur();
        factuur1.setId(123L);
        factuur1.setKlant("Klant");
        factuur1.setPrice(1);

        Woning woning3 = new Woning();
        woning3.setBoekingen(new ArrayList<>());
        woning3.setId(123L);
        woning3.setName("Name");
        woning3.setPrice(1);
        woning3.setRented("Rented");
        woning3.setType("Type");

        Boeking boeking1 = new Boeking();
        boeking1.setAnnulering(annulering1);
        boeking1.setFactuur(factuur1);
        boeking1.setFinish_date(LocalDate.ofEpochDay(1L));
        boeking1.setId(123L);
        boeking1.setPrice(1);
        boeking1.setStatus("Status");
        boeking1.setType_boeking("Type boeking");
        boeking1.setWoning(woning3);
        when(boekingRepository.save((Boeking) org.mockito.Mockito.any())).thenReturn(boeking1);
        when(boekingRepository.findById((Long) org.mockito.Mockito.any())).thenReturn(ofResult);

        Woning woning4 = new Woning();
        woning4.setBoekingen(new ArrayList<>());
        woning4.setId(123L);
        woning4.setName("Name");
        woning4.setPrice(1);
        woning4.setRented("Rented");
        woning4.setType("Type");
        Optional<Woning> ofResult1 = Optional.of(woning4);
        when(woningRepository.findById((Long) org.mockito.Mockito.any())).thenReturn(ofResult1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/boekingen/{id}/{woningId}", 123L,
                123L);
        MockMvcBuilders.standaloneSetup(boekingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link BoekingController#assignWoningToBoeking(Long, Long)}
     */
    @Test
    void testAssignWoningToBoeking4() throws Exception {
        Woning woning = new Woning();
        woning.setBoekingen(new ArrayList<>());
        woning.setId(123L);
        woning.setName("Name");
        woning.setPrice(1);
        woning.setRented("Rented");
        woning.setType("Type");

        Annulering annulering = new Annulering();
        annulering.setFinish_date(LocalDate.ofEpochDay(1L));
        annulering.setId(123L);
        annulering.setName("Name");
        annulering.setPrice(1);
        annulering.setStatus("Status");
        annulering.setType_boeking("Type boeking");
        annulering.setWoning(woning);

        Factuur factuur = new Factuur();
        factuur.setId(123L);
        factuur.setKlant("Klant");
        factuur.setPrice(1);

        Woning woning1 = new Woning();
        woning1.setBoekingen(new ArrayList<>());
        woning1.setId(123L);
        woning1.setName("Name");
        woning1.setPrice(1);
        woning1.setRented("Rented");
        woning1.setType("Type");

        Boeking boeking = new Boeking();
        boeking.setAnnulering(annulering);
        boeking.setFactuur(factuur);
        boeking.setFinish_date(LocalDate.ofEpochDay(1L));
        boeking.setId(123L);
        boeking.setPrice(1);
        boeking.setStatus("Status");
        boeking.setType_boeking("Type boeking");
        boeking.setWoning(woning1);
        Optional<Boeking> ofResult = Optional.of(boeking);

        Woning woning2 = new Woning();
        woning2.setBoekingen(new ArrayList<>());
        woning2.setId(123L);
        woning2.setName("Name");
        woning2.setPrice(1);
        woning2.setRented("Rented");
        woning2.setType("Type");

        Annulering annulering1 = new Annulering();
        annulering1.setFinish_date(LocalDate.ofEpochDay(1L));
        annulering1.setId(123L);
        annulering1.setName("Name");
        annulering1.setPrice(1);
        annulering1.setStatus("Status");
        annulering1.setType_boeking("Type boeking");
        annulering1.setWoning(woning2);

        Factuur factuur1 = new Factuur();
        factuur1.setId(123L);
        factuur1.setKlant("Klant");
        factuur1.setPrice(1);

        Woning woning3 = new Woning();
        woning3.setBoekingen(new ArrayList<>());
        woning3.setId(123L);
        woning3.setName("Name");
        woning3.setPrice(1);
        woning3.setRented("Rented");
        woning3.setType("Type");

        Boeking boeking1 = new Boeking();
        boeking1.setAnnulering(annulering1);
        boeking1.setFactuur(factuur1);
        boeking1.setFinish_date(LocalDate.ofEpochDay(1L));
        boeking1.setId(123L);
        boeking1.setPrice(1);
        boeking1.setStatus("Status");
        boeking1.setType_boeking("Type boeking");
        boeking1.setWoning(woning3);
        when(boekingRepository.save((Boeking) org.mockito.Mockito.any())).thenReturn(boeking1);
        when(boekingRepository.findById((Long) org.mockito.Mockito.any())).thenReturn(ofResult);

        Woning woning4 = new Woning();
        woning4.setBoekingen(new ArrayList<>());
        woning4.setId(123L);
        woning4.setName("Name");
        woning4.setPrice(1);
        woning4.setRented("Rented");
        woning4.setType("Type");
        Optional<Woning> ofResult1 = Optional.of(woning4);
        when(woningRepository.findById((Long) org.mockito.Mockito.any())).thenReturn(ofResult1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/boekingen/{id}/{woningId}", 123L,
                123L);
        MockMvcBuilders.standaloneSetup(boekingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link BoekingController#deleteBoeking(Long)}
     */
    @Test
    void testDeleteBoeking() throws Exception {
        doNothing().when(boekingRepository).deleteById((Long) org.mockito.Mockito.any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/boekingen/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(boekingController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link BoekingController#deleteBoeking(Long)}
     */
    @Test
    void testDeleteBoeking2() throws Exception {
        doNothing().when(boekingRepository).deleteById((Long) org.mockito.Mockito.any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/boekingen/{id}", 123L);
        deleteResult.characterEncoding("Encoding");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(boekingController)
                .build()
                .perform(deleteResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link BoekingController#getAllBoekingen()}
     */
    @Test
    void testGetAllBoekingen() throws Exception {
        when(boekingRepository.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/boekingen");
        MockMvcBuilders.standaloneSetup(boekingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link BoekingController#getAllBoekingen()}
     */
    @Test
    void testGetAllBoekingen2() throws Exception {
        Woning woning = new Woning();
        woning.setBoekingen(new ArrayList<>());
        woning.setId(123L);
        woning.setName("Name");
        woning.setPrice(1);
        woning.setRented("Rented");
        woning.setType("Type");

        Annulering annulering = new Annulering();
        annulering.setFinish_date(LocalDate.ofEpochDay(1L));
        annulering.setId(123L);
        annulering.setName("Name");
        annulering.setPrice(1);
        annulering.setStatus("Status");
        annulering.setType_boeking("Type boeking");
        annulering.setWoning(woning);

        Factuur factuur = new Factuur();
        factuur.setId(123L);
        factuur.setKlant("Klant");
        factuur.setPrice(1);

        Woning woning1 = new Woning();
        woning1.setBoekingen(new ArrayList<>());
        woning1.setId(123L);
        woning1.setName("Name");
        woning1.setPrice(1);
        woning1.setRented("Rented");
        woning1.setType("Type");

        Boeking boeking = new Boeking();
        boeking.setAnnulering(annulering);
        boeking.setFactuur(factuur);
        boeking.setFinish_date(LocalDate.ofEpochDay(1L));
        boeking.setId(123L);
        boeking.setPrice(1);
        boeking.setStatus("Status");
        boeking.setType_boeking("Type boeking");
        boeking.setWoning(woning1);

        ArrayList<Boeking> boekingList = new ArrayList<>();
        boekingList.add(boeking);
        when(boekingRepository.findAll()).thenReturn(boekingList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/boekingen");
        MockMvcBuilders.standaloneSetup(boekingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"id\":123,\"finish_date\":[1970,1,2],\"status\":\"Status\",\"type_boeking\":\"Type boeking\",\"woning\":{\"id\":123"
                                        + ",\"type\":\"Type\",\"name\":\"Name\",\"price\":1,\"rented\":\"Rented\"},\"price\":1}]"));
    }

    /**
     * Method under test: {@link BoekingController#getBoekingById(Long)}
     */
    @Test
    void testGetBoekingById() throws Exception {
        Woning woning = new Woning();
        woning.setBoekingen(new ArrayList<>());
        woning.setId(123L);
        woning.setName("Name");
        woning.setPrice(1);
        woning.setRented("Rented");
        woning.setType("Type");

        Annulering annulering = new Annulering();
        annulering.setFinish_date(LocalDate.ofEpochDay(1L));
        annulering.setId(123L);
        annulering.setName("Name");
        annulering.setPrice(1);
        annulering.setStatus("Status");
        annulering.setType_boeking("Type boeking");
        annulering.setWoning(woning);

        Factuur factuur = new Factuur();
        factuur.setId(123L);
        factuur.setKlant("Klant");
        factuur.setPrice(1);

        Woning woning1 = new Woning();
        woning1.setBoekingen(new ArrayList<>());
        woning1.setId(123L);
        woning1.setName("Name");
        woning1.setPrice(1);
        woning1.setRented("Rented");
        woning1.setType("Type");

        Boeking boeking = new Boeking();
        boeking.setAnnulering(annulering);
        boeking.setFactuur(factuur);
        boeking.setFinish_date(LocalDate.ofEpochDay(1L));
        boeking.setId(123L);
        boeking.setPrice(1);
        boeking.setStatus("Status");
        boeking.setType_boeking("Type boeking");
        boeking.setWoning(woning1);
        Optional<Boeking> ofResult = Optional.of(boeking);
        when(boekingRepository.findById((Long) org.mockito.Mockito.any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/boekingen/{id}", 123L);
        MockMvcBuilders.standaloneSetup(boekingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"finish_date\":[1970,1,2],\"status\":\"Status\",\"type_boeking\":\"Type boeking\",\"woning\":{\"id\":123"
                                        + ",\"type\":\"Type\",\"name\":\"Name\",\"price\":1,\"rented\":\"Rented\"},\"price\":1}"));
    }

    /**
     * Method under test: {@link BoekingController#updateBoeking(Long, BoekingInputDto)}
     */
    @Test
    void testUpdateBoeking() throws Exception {
        Woning woning = new Woning();
        woning.setBoekingen(new ArrayList<>());
        woning.setId(123L);
        woning.setName("Name");
        woning.setPrice(1);
        woning.setRented("Rented");
        woning.setType("Type");

        Annulering annulering = new Annulering();
        annulering.setFinish_date(LocalDate.ofEpochDay(1L));
        annulering.setId(123L);
        annulering.setName("Name");
        annulering.setPrice(1);
        annulering.setStatus("Status");
        annulering.setType_boeking("Type boeking");
        annulering.setWoning(woning);

        Factuur factuur = new Factuur();
        factuur.setId(123L);
        factuur.setKlant("Klant");
        factuur.setPrice(1);

        Woning woning1 = new Woning();
        woning1.setBoekingen(new ArrayList<>());
        woning1.setId(123L);
        woning1.setName("Name");
        woning1.setPrice(1);
        woning1.setRented("Rented");
        woning1.setType("Type");

        Boeking boeking = new Boeking();
        boeking.setAnnulering(annulering);
        boeking.setFactuur(factuur);
        boeking.setFinish_date(LocalDate.ofEpochDay(1L));
        boeking.setId(123L);
        boeking.setPrice(1);
        boeking.setStatus("Status");
        boeking.setType_boeking("Type boeking");
        boeking.setWoning(woning1);
        Optional<Boeking> ofResult = Optional.of(boeking);

        Woning woning2 = new Woning();
        woning2.setBoekingen(new ArrayList<>());
        woning2.setId(123L);
        woning2.setName("Name");
        woning2.setPrice(1);
        woning2.setRented("Rented");
        woning2.setType("Type");

        Annulering annulering1 = new Annulering();
        annulering1.setFinish_date(LocalDate.ofEpochDay(1L));
        annulering1.setId(123L);
        annulering1.setName("Name");
        annulering1.setPrice(1);
        annulering1.setStatus("Status");
        annulering1.setType_boeking("Type boeking");
        annulering1.setWoning(woning2);

        Factuur factuur1 = new Factuur();
        factuur1.setId(123L);
        factuur1.setKlant("Klant");
        factuur1.setPrice(1);

        Woning woning3 = new Woning();
        woning3.setBoekingen(new ArrayList<>());
        woning3.setId(123L);
        woning3.setName("Name");
        woning3.setPrice(1);
        woning3.setRented("Rented");
        woning3.setType("Type");

        Boeking boeking1 = new Boeking();
        boeking1.setAnnulering(annulering1);
        boeking1.setFactuur(factuur1);
        boeking1.setFinish_date(LocalDate.ofEpochDay(1L));
        boeking1.setId(123L);
        boeking1.setPrice(1);
        boeking1.setStatus("Status");
        boeking1.setType_boeking("Type boeking");
        boeking1.setWoning(woning3);
        when(boekingRepository.save((Boeking) org.mockito.Mockito.any())).thenReturn(boeking1);
        when(boekingRepository.findById((Long) org.mockito.Mockito.any())).thenReturn(ofResult);

        Woning woning4 = new Woning();
        woning4.setBoekingen(new ArrayList<>());
        woning4.setId(123L);
        woning4.setName("Name");
        woning4.setPrice(1);
        woning4.setRented("Rented");
        woning4.setType("Type");

        BoekingInputDto boekingInputDto = new BoekingInputDto();
        boekingInputDto.setFinish_date(LocalDate.ofEpochDay(1L));
        boekingInputDto.setId(123L);
        boekingInputDto.setPrice(1);
        boekingInputDto.setStatus("Status");
        boekingInputDto.setType_boeking("Type boeking");
        boekingInputDto.setWoning(woning4);
        String content = (new ObjectMapper()).writeValueAsString(boekingInputDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/boekingen/{id}", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(boekingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"finish_date\":[1970,1,2],\"status\":\"Status\",\"type_boeking\":\"Type boeking\",\"woning\":{\"id\":123"
                                        + ",\"type\":\"Type\",\"name\":\"Name\",\"price\":1,\"rented\":\"Rented\"},\"price\":1}"));
    }

    /**
     * Method under test: {@link BoekingController#updateBoeking(Long, BoekingInputDto)}
     */
    @Test
    void testUpdateBoeking2() throws Exception {
        Woning woning = new Woning();
        woning.setBoekingen(new ArrayList<>());
        woning.setId(123L);
        woning.setName("Name");
        woning.setPrice(1);
        woning.setRented("Rented");
        woning.setType("Type");

        Annulering annulering = new Annulering();
        annulering.setFinish_date(LocalDate.ofEpochDay(1L));
        annulering.setId(123L);
        annulering.setName("Name");
        annulering.setPrice(1);
        annulering.setStatus("Status");
        annulering.setType_boeking("Type boeking");
        annulering.setWoning(woning);

        Factuur factuur = new Factuur();
        factuur.setId(123L);
        factuur.setKlant("Klant");
        factuur.setPrice(1);

        Woning woning1 = new Woning();
        woning1.setBoekingen(new ArrayList<>());
        woning1.setId(123L);
        woning1.setName("Name");
        woning1.setPrice(1);
        woning1.setRented("Rented");
        woning1.setType("Type");

        Boeking boeking = new Boeking();
        boeking.setAnnulering(annulering);
        boeking.setFactuur(factuur);
        boeking.setFinish_date(LocalDate.ofEpochDay(1L));
        boeking.setId(123L);
        boeking.setPrice(1);
        boeking.setStatus("Status");
        boeking.setType_boeking("Type boeking");
        boeking.setWoning(woning1);
        Optional<Boeking> ofResult = Optional.of(boeking);

        Woning woning2 = new Woning();
        woning2.setBoekingen(new ArrayList<>());
        woning2.setId(123L);
        woning2.setName("Name");
        woning2.setPrice(1);
        woning2.setRented("Rented");
        woning2.setType("Type");

        Annulering annulering1 = new Annulering();
        annulering1.setFinish_date(LocalDate.ofEpochDay(1L));
        annulering1.setId(123L);
        annulering1.setName("Name");
        annulering1.setPrice(1);
        annulering1.setStatus("Status");
        annulering1.setType_boeking("Type boeking");
        annulering1.setWoning(woning2);

        Factuur factuur1 = new Factuur();
        factuur1.setId(123L);
        factuur1.setKlant("Klant");
        factuur1.setPrice(1);

        Woning woning3 = new Woning();
        woning3.setBoekingen(new ArrayList<>());
        woning3.setId(123L);
        woning3.setName("Name");
        woning3.setPrice(1);
        woning3.setRented("Rented");
        woning3.setType("Type");

        Boeking boeking1 = new Boeking();
        boeking1.setAnnulering(annulering1);
        boeking1.setFactuur(factuur1);
        boeking1.setFinish_date(LocalDate.ofEpochDay(1L));
        boeking1.setId(123L);
        boeking1.setPrice(1);
        boeking1.setStatus("Status");
        boeking1.setType_boeking("Type boeking");
        boeking1.setWoning(woning3);
        when(boekingRepository.save((Boeking) org.mockito.Mockito.any())).thenReturn(boeking1);
        when(boekingRepository.findById((Long) org.mockito.Mockito.any())).thenReturn(ofResult);

        Woning woning4 = new Woning();
        woning4.setBoekingen(new ArrayList<>());
        woning4.setId(123L);
        woning4.setName("Name");
        woning4.setPrice(1);
        woning4.setRented("Rented");
        woning4.setType("Type");

        BoekingInputDto boekingInputDto = new BoekingInputDto();
        boekingInputDto.setFinish_date(LocalDate.ofEpochDay(1L));
        boekingInputDto.setId(123L);
        boekingInputDto.setPrice(1);
        boekingInputDto.setStatus("Status");
        boekingInputDto.setType_boeking("Type boeking");
        boekingInputDto.setWoning(woning4);
        String content = (new ObjectMapper()).writeValueAsString(boekingInputDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/boekingen/{id}", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(boekingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"finish_date\":[1970,1,2],\"status\":\"Status\",\"type_boeking\":\"Type boeking\",\"woning\":{\"id\":123"
                                        + ",\"type\":\"Type\",\"name\":\"Name\",\"price\":1,\"rented\":\"Rented\"},\"price\":1}"));
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
