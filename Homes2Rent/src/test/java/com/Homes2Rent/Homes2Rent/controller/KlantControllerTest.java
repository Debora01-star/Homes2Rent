package com.Homes2Rent.Homes2Rent.controller;

import com.Homes2Rent.Homes2Rent.dto.KlantDto;
import com.Homes2Rent.Homes2Rent.dto.KlantInputDto;
import com.Homes2Rent.Homes2Rent.exceptions.DuplicatedEntryException;
import com.Homes2Rent.Homes2Rent.model.Klant;
import com.Homes2Rent.Homes2Rent.security.JwtService;
import com.Homes2Rent.Homes2Rent.service.KlantService;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(KlantController.class)
@AutoConfigureMockMvc(addFilters = false)

class KlantControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @MockBean
    private KlantService service;

    @MockBean
    JwtService jwtService;

    Klant klant1;

    KlantDto klantDto1;
    KlantDto klantDto2;

    KlantInputDto klantInputDto1;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        klant1 = new Klant(1L,"test@email.nl", "testnaam1", "gebruiker", "teststraat 77", "teststad", "1234AB");

        klantDto1 = new KlantDto(1L,"test@email.nl", "testnaam1", "gebruiker", "teststraat 77", "teststad", "1234AB");
        klantDto2 = new KlantDto(1L,"test@email.nl", "Nieuwe naam", "nieuwe achternaam", "teststraat 77", "teststad", "1234AB");

        klantInputDto1 = new KlantInputDto(1L,"test@email.nl", "testnaam1", "gebruiker", "teststraat 77", "teststad", "1234AB");
    }

    @Test
    @WithMockUser
    void saveKlant() throws Exception, DuplicatedEntryException {
        given(service.addKlant(any(KlantInputDto.class))).willReturn(klantDto1);

        mockMvc.perform(post("/klanten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(klantInputDto1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("email").value("test@email.nl"))
                .andExpect(jsonPath("firstname").value("testnaam"))
                .andExpect(jsonPath("lastname").value("gebruiker"))
                .andExpect(jsonPath("address").value("teststraat 77"))
                .andExpect(jsonPath("city").value("teststad"))
                .andExpect(jsonPath("zipcode").value("1234AB"));
    }

    @Test
    void updateKlant() throws Exception, DuplicatedEntryException {
        given(service.updateKlant(1L,klantInputDto1)).willReturn(klantDto2);

        mockMvc.perform(put("/klanten/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(klantInputDto1)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void getKlanten() throws Exception {
        given(service.getAllKlanten()).willReturn(List.of(klantDto1));

        mockMvc.perform(get("/klanten"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].email").value("test@email.nl"))
                .andExpect(jsonPath("$[0].firstname").value("testnaam"))
                .andExpect(jsonPath("$[0].lastname").value("gebruiker"))
                .andExpect(jsonPath("$[0].address").value("teststraat 77"))
                .andExpect(jsonPath("$[0].city").value("teststad"))
                .andExpect(jsonPath("$[0].zipcode").value("1234AB"));
    }

    @Test
    @WithMockUser
    void getKlant() throws Exception {
        Long id = 1L;
        given(service.getKlant(id)).willReturn(klantDto1);
        mockMvc.perform(get("/klanten/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("email").value("test@email.nl"))
                .andExpect(jsonPath("firstname").value("testnaam"))
                .andExpect(jsonPath("lastname").value("gebruiker"))
                .andExpect(jsonPath("address").value("teststraat 77"))
                .andExpect(jsonPath("city").value("teststad"))
                .andExpect(jsonPath("zipcode").value("1234AB"));
    }

    @Test
    @WithMockUser
    void deleteKlant() throws Exception {
        mockMvc.perform(delete("/klanten/1"))
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
