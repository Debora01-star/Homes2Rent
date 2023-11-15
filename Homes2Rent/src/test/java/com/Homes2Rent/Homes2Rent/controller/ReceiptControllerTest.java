package com.Homes2Rent.Homes2Rent.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.Homes2Rent.Homes2Rent.dto.ReceiptDto;
import com.Homes2Rent.Homes2Rent.model.Receipt;
import com.Homes2Rent.Homes2Rent.repository.ReceiptRepository;
import com.Homes2Rent.Homes2Rent.service.ReceiptService;
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

@ContextConfiguration(classes = {ReceiptController.class, ReceiptService.class})
@ExtendWith(SpringExtension.class)
class ReceiptControllerTest {
    @Autowired
    private ReceiptController receiptController;

    @MockBean
    private ReceiptRepository receiptRepository;

    /**
     * Method under test: {@link ReceiptController#addReceipt(ReceiptDto)}
     */
    @Test
    void testAddReceipt() throws Exception {
        Receipt receipt = new Receipt();
        receipt.setId(123L);
        receipt.setKlant("Klant");
        receipt.setPrice(1);
        when(receiptRepository.save((Receipt) any())).thenReturn(receipt);

        ReceiptDto receiptDto = new ReceiptDto();
        receiptDto.setBooking("Booking");
        receiptDto.setId(123L);
        receiptDto.setPrice(1);
        String content = (new ObjectMapper()).writeValueAsString(receiptDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/receipt")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(receiptController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"id\":123,\"boeking\":\"Booking\",\"price\":1}"));
    }

    /**
     * Method under test: {@link ReceiptController#deleteReceipt(Long)}
     */
    @Test
    void testDeleteReceipt() throws Exception {
        doNothing().when(receiptRepository).deleteById((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/receipt/{id}", 123L);
        MockMvcBuilders.standaloneSetup(receiptController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link ReceiptController#deleteReceipt(Long)}
     */
    @Test
    void testDeleteReceipt2() throws Exception {
        doNothing().when(receiptRepository).deleteById((Long) any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/receipt/{id}", 123L);
        deleteResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(receiptController)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link ReceiptController#getAllReceipt()}
     */
    @Test
    void testGetAllReceipt() throws Exception {
        when(receiptRepository.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/receipt");
        MockMvcBuilders.standaloneSetup(receiptController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link ReceiptController#getAllReceipt()}
     */
    @Test
    void testGetAllReceipt2() throws Exception {
        Receipt receipt = new Receipt();
        receipt.setId(123L);
        receipt.setKlant("?");
        receipt.setPrice(1);

        ArrayList<Receipt> receiptList = new ArrayList<>();
        receiptList.add(receipt);
        when(receiptRepository.findAll()).thenReturn(receiptList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/receipt");
        MockMvcBuilders.standaloneSetup(receiptController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[{\"id\":123,\"booking\":null,\"price\":1}]"));
    }

    /**
     * Method under test: {@link ReceiptController#getReceipt(Long)}
     */
    @Test
    void testGetReceipt() throws Exception {
        Receipt receipt = new Receipt();
        receipt.setId(123L);
        receipt.setKlant("Klant");
        receipt.setPrice(1);
        Optional<Receipt> ofResult = Optional.of(receipt);
        when(receiptRepository.findById((Long) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/receipt/{id}", 123L);
        MockMvcBuilders.standaloneSetup(receiptController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"id\":123,\"booking\":null,\"price\":1}"));
    }

    /**
     * Method under test: {@link ReceiptController#updateReceipt(Long, ReceiptDto)}
     */
    @Test
    void testUpdateReceipt() throws Exception {
        Receipt receipt = new Receipt();
        receipt.setId(123L);
        receipt.setKlant("Klant");
        receipt.setPrice(1);
        Optional<Receipt> ofResult = Optional.of(receipt);

        Receipt receipt1 = new Receipt();
        receipt1.setId(123L);
        receipt1.setKlant("Klant");
        receipt1.setPrice(1);
        when(receiptRepository.save((Receipt) any())).thenReturn(receipt1);
        when(receiptRepository.findById((Long) any())).thenReturn(ofResult);
        when(receiptRepository.existsById((Long) any())).thenReturn(true);

        ReceiptDto receiptDto = new ReceiptDto();
        receiptDto.setBooking("Booking");
        receiptDto.setId(123L);
        receiptDto.setPrice(1);
        String content = (new ObjectMapper()).writeValueAsString(receiptDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/receipt/{id}", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(receiptController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"id\":123,\"booking\":\"Booking\",\"price\":1}"));
    }
}

