package com.Homes2Rent.Homes2Rent.service;

import com.Homes2Rent.Homes2Rent.dto.WoningDto;
import com.Homes2Rent.Homes2Rent.dto.WoningInputDto;
import com.Homes2Rent.Homes2Rent.exceptions.DuplicatedEntryException;
import com.Homes2Rent.Homes2Rent.exceptions.RecordNotFoundException;
import com.Homes2Rent.Homes2Rent.model.Customer;
import com.Homes2Rent.Homes2Rent.model.Woning;
import com.Homes2Rent.Homes2Rent.repository.CustomerRepository;
import com.Homes2Rent.Homes2Rent.repository.UserRepository;
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
import org.springframework.security.core.userdetails.User;

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
        CustomerRepository customerRepository;

        @InjectMocks
        WoningService woningService;

        @Captor
        ArgumentCaptor<Woning> argumentCaptor;

        Woning woning1;
        Woning woning2;

        Customer customer1;

        Customer customer2;

        @BeforeEach
        void setUp() {
            customer1 = new Customer(1L, "secret@email.com","name1","lastname1","streetname1","city1","zipcode1");
            customer2 = new Customer(2L, "secret2@email.com","name2","lastname2","streetname2","city2","zipcode2");

            woning1 = new Woning(1L, "huis", "villa happiness", 1500, "rented");
            woning2 = new Woning( 2L, "appartament", "cozy rooms", 750, "rented");
        }

        @Test
        void createWoning() throws DuplicatedEntryException {
            WoningDto dto = new WoningDto(1L,"huis","villa happiness", 1500, "rented");

            given(customerRepository.findById(1L)).willReturn(Optional.of(customer1));
            when(woningRepository.save(woning1)).thenReturn(woning1);

            woningService.addWoning(dto);
            verify(woningRepository, times(1)).save(argumentCaptor.capture());
            Woning woning = argumentCaptor.getValue();

            assertEquals(woning1.getPrice(), woning.getPrice());
            assertEquals(woning1.getType(), woning.getType());
            assertEquals(woning1.getName(), woning.getName());
            assertEquals(woning1.getRented(), woning.getRented());

        }

        @Test
        void updateWoning() throws DuplicatedEntryException {
            when(woningRepository.findById(1L)).thenReturn(Optional.of(woning1));
            when(woningRepository.existsById(any())).thenReturn(true);
            given(customerRepository.findById(1L)).willReturn(Optional.of(customer1));

            WoningInputDto dto = new WoningInputDto(1L,"huis","villa happiness", 1500, "rented");

            when(woningRepository.save(woning1)).thenReturn(woning1);

            woningService.updateWoning(1L, new WoningDto());

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
            assertThrows(RecordNotFoundException.class, () -> woningService.updateWoning(1L,  new WoningDto(1L, "huis", "villa happiness", 1500, "rented")));
        }

        @Test
        void updateWoningThrowsNotFoundExceptionTest() {
            WoningInputDto woningInputDto = new WoningInputDto(1L,"huis","villa happiness", 1500, "rented");
            assertThrows(RecordNotFoundException.class, () -> woningService.updateWoning(null,new WoningDto()));
        }

        @Test
        void getWoningThrowsExceptionTest() {
            assertThrows(RecordNotFoundException.class, () -> woningService.getWoning(null));
        }

        @Test
        void updateWoningThrowsExceptionForKlantTest() {
            when(woningRepository.findById(any())).thenReturn(Optional.of(woning1));
            assertThrows(RecordNotFoundException.class, () -> woningService.updateWoning(3L,new WoningDto(1L,"huis","villa happiness", 1500, "rented")));
        }
    }

