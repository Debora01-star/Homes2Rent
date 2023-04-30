package com.Homes2Rent.Homes2Rent.service;

import com.Homes2Rent.Homes2Rent.dto.BoekingDto;
import com.Homes2Rent.Homes2Rent.dto.BoekingInputDto;
import com.Homes2Rent.Homes2Rent.exceptions.RecordNotFoundException;
import com.Homes2Rent.Homes2Rent.model.Annulering;
import com.Homes2Rent.Homes2Rent.model.Boeking;
import com.Homes2Rent.Homes2Rent.model.Factuur;
import com.Homes2Rent.Homes2Rent.model.Woning;
import com.Homes2Rent.Homes2Rent.repository.BoekingRepository;
import com.Homes2Rent.Homes2Rent.repository.WoningRepository;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BoekingServiceTest {

    @Mock
    BoekingRepository boekingRepository;

    @Mock
    WoningRepository woningRepository;

    @InjectMocks
    BoekingService boekingService;

    @Captor
    ArgumentCaptor<Boeking> argumentCaptor;

    Boeking boeking1;
    Boeking boeking2;

    Woning woning1;
    Woning woning2;

    @BeforeEach
    void setUp() {
        woning1 = new Woning(1L, "huis", "villa happiness", 1500, "rented");
        woning2 = new Woning(1L, "appartament", "cozy rooms", 750, "rented");

        boeking1 = new Boeking(1L, LocalDate.of(2023, 03, 15), "status", "geboekt", 1500, woning1);
        boeking2 = new Boeking(2L, LocalDate.of(2023, 03, 21), "status", "geboekt", 750, woning2);
    }

    @Test
    void createBoeking() {
        BoekingInputDto dto = new BoekingInputDto(LocalDate.of(2023, 03, 15), 1L, "status", "geboekt", woning1, 1500);

        given(woningRepository.findById(1L)).willReturn(Optional.of(woning1));
        when(boekingRepository.save(boeking1)).thenReturn(boeking1);

        boekingService.addBoeking(dto);
        verify(boekingRepository, times(1)).save(argumentCaptor.capture());
        Boeking boeking = argumentCaptor.getValue();

        assertEquals(boeking1.getType_boeking(), boeking.getType_boeking());
        assertEquals(boeking1.getStatus(), boeking.getStatus());
        assertEquals(boeking1.getFinish_date(), boeking.getFinish_date());
        assertEquals(boeking1.getPrice(), boeking.getPrice());
        assertEquals(boeking1.getWoning(), boeking.getWoning());

    }

    /**
     * Method under test: {@link BoekingService#updateBoeking(Long, BoekingInputDto)}
     */
    @Test
    void testUpdateBoeking() {
        assertThrows(RecordNotFoundException.class, () -> boekingService.updateBoeking(123L, new BoekingInputDto()));
    }

    @Test
    void updateBoeking() {
        when(boekingRepository.findById(1L)).thenReturn(Optional.of(boeking1));
        given(woningRepository.findById(1L)).willReturn(Optional.of(woning1));
        BoekingInputDto dto = new BoekingInputDto(LocalDate.of(2023, 03, 15), 1L, "status", "geboekt", woning1, 1500);

        when(boekingRepository.save(boekingService.transferToBoeking(dto))).thenReturn(boeking1);

        boekingService.updateBoeking(1L, dto);

        verify(boekingRepository, times(1)).save(argumentCaptor.capture());

        Boeking captured = argumentCaptor.getValue();

        assertEquals(dto.getType_boeking(), captured.getType_boeking());
        assertEquals(dto.getStatus(), captured.getStatus());
        assertEquals(dto.getFinish_date(), captured.getFinish_date());
        assertEquals(dto.getId(), captured.getId());
        assertEquals(dto.getPrice(), captured.getPrice());
        assertEquals(dto.getWoning(), captured.getWoning());


    }

    @Test
    void deleteBoeking() {
        boekingService.deleteBoeking(1L);

        verify(boekingRepository).deleteById(1L);
    }


    @Test
    void testDeleteBoeking2() {
        boekingService.deleteBoeking(1L);
        assertEquals(77, boekingService.getAllBoekingen().size());
    }

    @Test
    void getAllBoekingen() {

        when(boekingRepository.findAll()).thenReturn(List.of(boeking1, boeking2));

        List<Boeking> boekingen = (List<Boeking>) boekingRepository.findAll();
        List<BoekingDto> dtos = boekingService.getAllBoekingen();

        assertEquals(boekingen.get(0).getType_boeking(), dtos.get(0).getType_boeking());
        assertEquals(boekingen.get(0).getFinish_date(), dtos.get(0).getFinish_date());
        assertEquals(boekingen.get(0).getStatus(), dtos.get(0).getStatus());
        assertEquals(boekingen.get(0).getWoning(), dtos.get(0).getWoning());
        assertEquals(boekingen.get(0).getId(), dtos.get(0).getId());
        assertEquals(boekingen.get(0).getPrice(), dtos.get(0).getPrice());
    }

    /**
     * Method under test: {@link BoekingService#getAllBoekingen()}
     */
    @Test
    void testGetAllBoekingen() {
        assertEquals(77, boekingService.getAllBoekingen().size());
    }

    /**
     * Method under test: {@link BoekingService#transferBoekingListToDtoList(List)}
     */
    @Test
    void testTransferBoekingListToDtoList() {
        assertTrue(boekingService.transferBoekingListToDtoList(new ArrayList<>()).isEmpty());
    }

    /**
     * Method under test: {@link BoekingService#transferBoekingListToDtoList(List)}
     */
    @Test
    void testTransferBoekingListToDtoList2() {
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
        assertEquals(1, boekingService.transferBoekingListToDtoList(boekingList).size());
    }

    /**
     * Method under test: {@link BoekingService#getBoekingById(Long)}
     */
    @Test
    void testGetBoekingById() {
        assertThrows(RecordNotFoundException.class, () -> boekingService.getBoekingById(123L));
    }


    @Test
    void testAddBoeking2() {
        BoekingInputDto boekingInputDto = new BoekingInputDto();
        boekingInputDto.setStatus("Status");
        BoekingDto actualAddBoekingResult = boekingService.addBoeking(boekingInputDto);
        assertNull(actualAddBoekingResult.getFinish_date());
        assertNull(actualAddBoekingResult.getWoning());
        assertNull(actualAddBoekingResult.getType_boeking());
        assertEquals("Status", actualAddBoekingResult.getStatus());
        assertNull(actualAddBoekingResult.getPrice());
    }

    @Test
    void getBoeking() {
        Long id = 1L;
        when(boekingRepository.findById(id)).thenReturn(Optional.of(boeking2));

        Boeking boeking = boekingRepository.findById(id).get();
        BoekingDto dto = boekingService.getBoekingById(id);

        assertEquals(boeking.getWoning(), dto.getWoning());
        assertEquals(boeking.getType_boeking(), dto.getType_boeking());
        assertEquals(boeking.getStatus(), dto.getStatus());
        assertEquals(boeking.getFinish_date(), dto.getFinish_date());
    }

    @Test
    void updateBoekingThrowsExceptionTest() {
        assertThrows(RecordNotFoundException.class, () -> boekingService.updateBoeking(1L, new BoekingInputDto(LocalDate.of(2023, 03, 15), 1L, "status", "geboekt", woning1, 1500)));
    }

    @Test
    void getBoekingThrowsExceptionTest() {
        assertThrows(RecordNotFoundException.class, () -> boekingService.getBoekingById(null));
    }

    @Test
    void updateBoekingThrowsExceptionForBoekingTest() {
        when(boekingRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(RecordNotFoundException.class, () -> boekingService.updateBoeking(1L, new BoekingInputDto(LocalDate.of(2023, 03, 15), 1L, "status", "geboekt", woning2, 750)));
    }

    /**
     * Method under test: {@link BoekingService#transferToBoeking(BoekingInputDto)}
     */
    @Test
    void testTransferToBoeking() {
        Boeking actualTransferToBoekingResult = boekingService.transferToBoeking(new BoekingInputDto());
        assertNull(actualTransferToBoekingResult.getWoning());
        assertNull(actualTransferToBoekingResult.getType_boeking());
        assertNull(actualTransferToBoekingResult.getStatus());
        assertNull(actualTransferToBoekingResult.getPrice());
        assertNull(actualTransferToBoekingResult.getFinish_date());
    }

    /**
     * Method under test: {@link BoekingService#assignFactuurToBoeking(Long, Long)}
     */
    @Test
    void testAssignFactuurToBoeking() {
        assertThrows(RecordNotFoundException.class, () -> boekingService.assignFactuurToBoeking(123L, 123L));
        assertThrows(RecordNotFoundException.class, () -> boekingService.assignFactuurToBoeking(1L, 123L));
    }

    /**
     * Method under test: {@link BoekingService#assignFactuurToBoeking(Long, Long)}
     */
    @Test
    void testAssignFactuurToBoeking2() {
        boekingService.assignFactuurToBoeking(1L, 1L);
        assertEquals(78, boekingService.getAllBoekingen().size());
    }

    /**
     * Method under test: {@link BoekingService#assignAnnuleringToBoeking(Long, Long)}
     */
    @Test
    void testAssignAnnuleringToBoeking() {
        assertThrows(RecordNotFoundException.class, () -> boekingService.assignAnnuleringToBoeking(123L, 123L));
        assertThrows(RecordNotFoundException.class, () -> boekingService.assignAnnuleringToBoeking(1L, 123L));
    }

    /**
     * Method under test: {@link BoekingService#assignAnnuleringToBoeking(Long, Long)}
     */
    @Test
    void testAssignAnnuleringToBoeking2() {
        boekingService.assignAnnuleringToBoeking(1L, 1L);
        assertEquals(78, boekingService.getAllBoekingen().size());
    }

    /**
     * Method under test: {@link BoekingService#assignWoningToBoeking(Long, Long)}
     */
    @Test
    void testAssignWoningToBoeking() {
        assertThrows(RecordNotFoundException.class, () -> boekingService.assignWoningToBoeking(123L, 123L));
        assertThrows(RecordNotFoundException.class, () -> boekingService.assignWoningToBoeking(1L, 123L));
    }

    /**
     * Method under test: {@link BoekingService#assignWoningToBoeking(Long, Long)}
     */
    @Test
    void testAssignWoningToBoeking2() {
        boekingService.assignWoningToBoeking(1L, 1L);
        assertEquals(78, boekingService.getAllBoekingen().size());
    }
}
