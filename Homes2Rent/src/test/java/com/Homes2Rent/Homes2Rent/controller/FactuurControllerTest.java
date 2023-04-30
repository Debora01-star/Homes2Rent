package com.Homes2Rent.Homes2Rent.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.Homes2Rent.Homes2Rent.dto.FactuurDto;
import com.Homes2Rent.Homes2Rent.model.Factuur;
import com.Homes2Rent.Homes2Rent.repository.FactuurRepository;
import com.Homes2Rent.Homes2Rent.service.FactuurService;
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

@ContextConfiguration(classes = {FactuurController.class, FactuurService.class})
@ExtendWith(SpringExtension.class)
class FactuurControllerTest {
    @Autowired
    private FactuurController factuurController;

    @MockBean
    private FactuurRepository factuurRepository;

    /**
     * Method under test: {@link FactuurController#addFactuur(FactuurDto)}
     */
    @Test
    void testAddFactuur() throws Exception {
        Factuur factuur = new Factuur();
        factuur.setId(123L);
        factuur.setKlant("Klant");
        factuur.setPrice(1);
        when(factuurRepository.save((Factuur) any())).thenReturn(factuur);

        FactuurDto factuurDto = new FactuurDto();
        factuurDto.setBoeking("Boeking");
        factuurDto.setId(123L);
        factuurDto.setPrice(1);
        String content = (new ObjectMapper()).writeValueAsString(factuurDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/facturen")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(factuurController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"id\":123,\"boeking\":\"Boeking\",\"price\":1}"));
    }

    /**
     * Method under test: {@link FactuurController#deleteFactuur(Long)}
     */
    @Test
    void testDeleteFactuur() throws Exception {
        doNothing().when(factuurRepository).deleteById((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/facturen/{id}", 123L);
        MockMvcBuilders.standaloneSetup(factuurController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link FactuurController#deleteFactuur(Long)}
     */
    @Test
    void testDeleteFactuur2() throws Exception {
        doNothing().when(factuurRepository).deleteById((Long) any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/facturen/{id}", 123L);
        deleteResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(factuurController)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link FactuurController#getAllFactuur()}
     */
    @Test
    void testGetAllFactuur() throws Exception {
        when(factuurRepository.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/facturen");
        MockMvcBuilders.standaloneSetup(factuurController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link FactuurController#getAllFactuur()}
     */
    @Test
    void testGetAllFactuur2() throws Exception {
        Factuur factuur = new Factuur();
        factuur.setId(123L);
        factuur.setKlant("?");
        factuur.setPrice(1);

        ArrayList<Factuur> factuurList = new ArrayList<>();
        factuurList.add(factuur);
        when(factuurRepository.findAll()).thenReturn(factuurList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/facturen");
        MockMvcBuilders.standaloneSetup(factuurController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[{\"id\":123,\"boeking\":null,\"price\":1}]"));
    }

    /**
     * Method under test: {@link FactuurController#getFactuur(Long)}
     */
    @Test
    void testGetFactuur() throws Exception {
        Factuur factuur = new Factuur();
        factuur.setId(123L);
        factuur.setKlant("Klant");
        factuur.setPrice(1);
        Optional<Factuur> ofResult = Optional.of(factuur);
        when(factuurRepository.findById((Long) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/facturen/{id}", 123L);
        MockMvcBuilders.standaloneSetup(factuurController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"id\":123,\"boeking\":null,\"price\":1}"));
    }

    /**
     * Method under test: {@link FactuurController#updateFactuur(Long, FactuurDto)}
     */
    @Test
    void testUpdateFactuur() throws Exception {
        Factuur factuur = new Factuur();
        factuur.setId(123L);
        factuur.setKlant("Klant");
        factuur.setPrice(1);
        Optional<Factuur> ofResult = Optional.of(factuur);

        Factuur factuur1 = new Factuur();
        factuur1.setId(123L);
        factuur1.setKlant("Klant");
        factuur1.setPrice(1);
        when(factuurRepository.save((Factuur) any())).thenReturn(factuur1);
        when(factuurRepository.findById((Long) any())).thenReturn(ofResult);
        when(factuurRepository.existsById((Long) any())).thenReturn(true);

        FactuurDto factuurDto = new FactuurDto();
        factuurDto.setBoeking("Boeking");
        factuurDto.setId(123L);
        factuurDto.setPrice(1);
        String content = (new ObjectMapper()).writeValueAsString(factuurDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/facturen/{id}", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(factuurController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"id\":123,\"boeking\":\"Boeking\",\"price\":1}"));
    }
}

