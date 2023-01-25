package com.Homes2Rent.Homes2Rent.service;

import com.Homes2Rent.Homes2Rent.dto.BoekingDto;
import com.Homes2Rent.Homes2Rent.dto.BoekingInputDto;
import com.Homes2Rent.Homes2Rent.exceptions.RecordNotFoundException;
import com.Homes2Rent.Homes2Rent.model.Boeking;
import com.Homes2Rent.Homes2Rent.model.Woning;
import com.Homes2Rent.Homes2Rent.repository.BoekingRepository;
import com.Homes2Rent.Homes2Rent.repository.WoningRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


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
            woning1 = new Woning(1L,"huis","villa happiness", 1500, "rented");
            woning2 = new Woning(1L,"appartament", "cozy rooms", 750, "rented");

            boeking1 = new Boeking(1L, LocalDate.of(2022,03,15), "notes", "status", "geboekt", "woning1", 1500);
            boeking2 = new Boeking(2L, LocalDate.of(2022,03,21), "notes", "status", "geboekt", "woning2", 750);
        }

        @Test
        void createBoeking() {
            BoekingInputDto dto = new BoekingInputDto(LocalDate.of(2022,03,15), 1L, "notes", "status", "geboekt", "woning1", 1500);

            given(woningRepository.findById(1L)).willReturn(Optional.of(woning1));
            when(boekingRepository.save(boeking1)).thenReturn(boeking1);

            boekingService.addBoeking(dto);
            verify(boekingRepository, times(1)).save(argumentCaptor.capture());
            Boeking boeking = argumentCaptor.getValue();

            assertEquals(boeking1.getType_boeking(), boeking.getType_boeking());
            assertEquals(boeking1.getNotes(), boeking.getNotes());
            assertEquals(boeking1.getStatus(), boeking.getStatus());
            assertEquals(boeking1.getFinish_date(), boeking.getFinish_date());
            assertEquals(boeking1.getId(), boeking.getId());
            assertEquals(boeking1.getPrice(), boeking.getPrice());
            assertEquals(boeking1.getWoning(), boeking.getWoning());

        }

        @Test
        void updateBoeking() {
            when(boekingRepository.findById(1L)).thenReturn(Optional.of(boeking1));
            given(woningRepository.findById(1L)).willReturn(Optional.of(woning1));
            BoekingInputDto dto = new BoekingInputDto(LocalDate.of(2022,03,15), 1L, "notes", "status", "geboekt", "woning1", 1500);

            when(boekingRepository.save(boekingService.transferToBoeking(dto))).thenReturn(boeking1);

            boekingService.updateBoeking(dto,1L);

            verify(boekingRepository, times(1)).save(argumentCaptor.capture());

            Boeking captured = argumentCaptor.getValue();

            assertEquals(dto.getType_boeking(), captured.getType_boeking());
            assertEquals(dto.getNotes(), captured.getNotes());
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
        void getAllBoekingen() {

            when(boekingRepository.findAll()).thenReturn(List.of(boeking1, boeking2));

            List<Boeking> boekingen = (List<Boeking>) boekingRepository.findAll();
            List<BoekingDto> dtos = boekingService.getAllBoekingen();

            assertEquals(boekingen.get(0).getType_boeking(), dtos.get(0).getType_boeking());
            assertEquals(boekingen.get(0).getFinish_date(), dtos.get(0).getFinish_date());
            assertEquals(boekingen.get(0).getNotes(), dtos.get(0).getNotes());
            assertEquals(boekingen.get(0).getStatus(), dtos.get(0).getStatus());
            assertEquals(boekingen.get(0).getWoning(), dtos.get(0).getWoning());
            assertEquals(boekingen.get(0).getId(), dtos.get(0).getId());
            assertEquals(boekingen.get(0).getPrice(), dtos.get(0).getPrice());
        }

        @Test
        void getBoeking() {
            Long id = 1L;
            when(boekingRepository.findById(id)).thenReturn(Optional.of(boeking2));

            Boeking boeking = boekingRepository.findById(id).get();
            BoekingDto dto = boekingService.getBoeking(id);

            assertEquals(boeking.getWoning(), dto.getWoning());
            assertEquals(boeking.getType_boeking(), dto.getType_boeking());
            assertEquals(boeking.getStatus(), dto.getStatus());
            assertEquals(boeking.getNotes(), dto.getNotes());
            assertEquals(boeking.getFinish_date(), dto.getFinish_date());
        }

        @Test
        void updateBoekingThrowsExceptionTest() {
            assertThrows(RecordNotFoundException.class, () -> boekingService.updateBoeking(new BoekingInputDto(LocalDate.of(2022,03,15), 1L, "notes", "status", "geboekt", "woning1", 1500 ), 1L));
        }

        @Test
        void getBoekingThrowsExceptionTest() {
            assertThrows(RecordNotFoundException.class, () -> boekingService.getBoeking(null));
        }

        @Test
        void updateBoekingThrowsExceptionForBoekingTest() {
            when(boekingRepository.findById(any())).thenReturn(Optional.of(boeking1));
            assertThrows(RecordNotFoundException.class, () -> boekingService.updateBoeking(new BoekingInputDto(LocalDate.of(2022,03,15), 1L, "notes", "status", "geboekt", "woning1", 750), 2L));
        }
    }
