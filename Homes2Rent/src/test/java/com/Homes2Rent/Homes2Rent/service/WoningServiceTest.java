package com.Homes2Rent.Homes2Rent.service;

import com.Homes2Rent.Homes2Rent.dto.WoningDto;
import com.Homes2Rent.Homes2Rent.dto.WoningInputDto;
import com.Homes2Rent.Homes2Rent.exceptions.DuplicatedEntryException;
import com.Homes2Rent.Homes2Rent.exceptions.RecordNotFoundException;
import com.Homes2Rent.Homes2Rent.model.Klant;
import com.Homes2Rent.Homes2Rent.model.Woning;
import com.Homes2Rent.Homes2Rent.repository.KlantRepository;
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

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;




    @ExtendWith(MockitoExtension.class)
    @MockitoSettings(strictness = Strictness.LENIENT)
    public class WoningServiceTest {

        @Mock
        WoningRepository woningRepository;

        @Mock
        KlantRepository klantRepository;

        @InjectMocks
        WoningService woningService;

        @Captor
        ArgumentCaptor<Woning> argumentCaptor;

        Woning woning1;
        Woning woning2;

        Klant klant1;
        Klant klant2;

        @BeforeEach
        void setUp() {
            klant1 = new Klant(1L, "test@email.nl","testnaam","gebruiker","teststraat 77","teststad","1234AB");
            klant2 = new Klant(1L, "test@email2.nl","testnaam2","gebruiker2","teststraat 88","teststad2","1234CD");

            woning1 = new Woning("huis", "villa happiness", 1L, 1500, "rented", "klant");
            woning2 = new Woning( 2L, "appartament", "cozy rooms", "klant");
        }

        @Test
        void createWoning() throws DuplicatedEntryException {
            WoningInputDto dto = new WoningInputDto(1L,"huis","villa hapiness", 1500, "rented");

            given(klantRepository.findById(1L)).willReturn(Optional.of(klant1));
            when(woningRepository.save(woning1)).thenReturn(woning1);

            woningService.addWoning(dto);
            verify(woningRepository, times(1)).save(argumentCaptor.capture());
            Woning woning = argumentCaptor.getValue();

            assertEquals(woning1.getId(), woning.getId());
            assertEquals(woning1.getPrice(), woning.getPrice());
            assertEquals(woning1.getType(), woning.getType());
            assertEquals(woning1.getName(), woning.getName());
            assertEquals(woning1.getRented(), woning.getRented());
            assertEquals(woning1.getKlant(), woning.getKlant());

        }

        @Test
        void updateWoning() throws DuplicatedEntryException {
            when(woningRepository.findById(1L)).thenReturn(Optional.of(woning1));
            given(klantRepository.findById(1L)).willReturn(Optional.of(klant1));

            WoningInputDto dto = new WoningInputDto(1L,"huis","villa hapiness", 1500, "rented");

            when(woningRepository.save(woningService.transferToWoning(dto))).thenReturn(woning1);

            woningService.updateWoning(1L, dto);

            verify(woningRepository, times(1)).save(argumentCaptor.capture());

            Woning captured = argumentCaptor.getValue();

            assertEquals(dto.getId(), captured.getId());
            assertEquals(dto.getPrice(), captured.getPrice());
            assertEquals(dto.getType(), captured.getType());
            assertEquals(dto.getName(), captured.getName());
            assertEquals(dto.getRented(), captured.getRented());
            assertEquals(dto.getPrice(), captured.getPrice());
        }

        @Test
        void deleteWoning() {
            woningService.deleteWoning(1L);

            verify(woningRepository).deleteById(1L);
        }

        @Test
        void getAllWoningen() {

            when(woningRepository.findAll()).thenReturn(List.of(woning1, woning2));

            List<Woning> woningen = (List<Woning>) woningRepository.findAll();
            Collection<WoningDto> dtos = woningService.getAllWoningen();

            assertEquals(woningen.get(0).getPrice(), dtos.stream().findFirst().get().getPrice());
            assertEquals(woningen.get(0).getId(), dtos.stream().findFirst().get().getId());
            assertEquals(woningen.get(0).getType(), dtos.stream().findFirst().get().getType());
            assertEquals(woningen.get(0).getName(), dtos.stream().findFirst().get().getName());
            assertEquals(woningen.get(0).getRented(), dtos.stream().findFirst().get().getRented());

        }

        @Test
        void getBoeking() {
            Long id = 1L;
            when(woningRepository.findById(id)).thenReturn(Optional.of(woning2));

            Woning woning = woningRepository.findById(id).get();
            WoningDto dto = woningService.getWoning(id);

            assertEquals(woning.getId(), dto.getId());
            assertEquals(woning.getPrice(), dto.getPrice());
            assertEquals(woning.getType(), dto.getType());
            assertEquals(woning.getName(), dto.getName());
            assertEquals(woning.getRented(), dto.getRented());

        }

        @Test
        void updateWoningThrowsExceptionTest() {
            assertThrows(RecordNotFoundException.class, () -> woningService.updateWoning(1L, new WoningInputDto(1L, "huis", "villa happiness", 1500, "rented")));
        }

        @Test
        void updateWoningThrowsNotFoundExceptionTest() {
            WoningInputDto woningInputDto = new WoningInputDto(1L,"huis","villa happiness", 1500, "rented");
            assertThrows(RecordNotFoundException.class, () -> woningService.updateWoning(null,woningInputDto));
        }

        @Test
        void getWoningThrowsExceptionTest() {
            assertThrows(RecordNotFoundException.class, () -> woningService.getWoning(null));
        }

        @Test
        void updateWoningThrowsExceptionForKlantTest() {
            when(woningRepository.findById(any())).thenReturn(Optional.of(woning1));
            assertThrows(RecordNotFoundException.class, () -> woningService.updateWoning(3L,new WoningInputDto(1L,"huis","villa happiness", 1500, "rented")));
        }
    }

