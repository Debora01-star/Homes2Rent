package com.Homes2Rent.Homes2Rent.service;

import com.Homes2Rent.Homes2Rent.dto.HomeDto;
import com.Homes2Rent.Homes2Rent.dto.HomeInputDto;
import com.Homes2Rent.Homes2Rent.exceptions.DuplicatedEntryException;
import com.Homes2Rent.Homes2Rent.exceptions.RecordNotFoundException;
import com.Homes2Rent.Homes2Rent.model.Customer;
import com.Homes2Rent.Homes2Rent.model.Home;
import com.Homes2Rent.Homes2Rent.repository.CustomerRepository;
import com.Homes2Rent.Homes2Rent.repository.HomeRepository;
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
    public class HomeServiceTest {

        @Mock
        HomeRepository homeRepository;

        @Mock
        CustomerRepository customerRepository;

        @InjectMocks
        HomeService homeService;

        @Captor
        ArgumentCaptor<Home> argumentCaptor;

        Home home1;
        Home home2;

        Customer customer1;

        Customer customer2;

        @BeforeEach
        void setUp() {
            customer1 = new Customer(1L, "secret@email.com","name1","lastname1","streetname1","city1","zipcode1");
            customer2 = new Customer(2L, "secret2@email.com","name2","lastname2","streetname2","city2","zipcode2");

            home1 = new Home(1L, "huis", "villa happiness", 1500, "rented");
            home2 = new Home( 2L, "appartament", "cozy rooms", 750, "rented");
        }

        @Test
        void createHome() throws DuplicatedEntryException {
            HomeDto dto = new HomeDto(1L,"huis","villa happiness", 1500, "rented");

            given(customerRepository.findById(1L)).willReturn(Optional.of(customer1));
            when(homeRepository.save(home1)).thenReturn(home1);

            homeService.addHome(dto);
            verify(homeRepository, times(1)).save(argumentCaptor.capture());
            Home home = argumentCaptor.getValue();

            assertEquals(home1.getPrice(), home.getPrice());
            assertEquals(home1.getType(), home.getType());
            assertEquals(home1.getName(), home.getName());
            assertEquals(home1.getRented(), home.getRented());

        }


        @Test
        void updateHome() throws DuplicatedEntryException {
            when(homeRepository.findById(1L)).thenReturn(Optional.of(home1));
            when(homeRepository.existsById(any())).thenReturn(true);
            given(customerRepository.findById(1L)).willReturn(Optional.of(customer1));

            HomeInputDto dto = new HomeInputDto(1L,"huis","villa happiness", 1500, "rented");

            when(homeRepository.save(home1)).thenReturn(home1);

            homeService.updateHome(1L, new HomeDto());

            verify(homeRepository, times(1)).save(argumentCaptor.capture());

            Home captured = argumentCaptor.getValue();

            assertEquals(dto.getId(), captured.getId());
            assertEquals(dto.getPrice(), captured.getPrice());
            assertEquals(dto.getType(), captured.getType());
            assertEquals(dto.getName(), captured.getName());
            assertEquals(dto.getRented(), captured.getRented());
            assertEquals(dto.getPrice(), captured.getPrice());
        }


        @Test
        void deleteHome() {
            homeService.deleteHome(1L);

            verify(homeRepository).deleteById(1L);
        }

        @Test
        void getAllHome() {

            when(homeRepository.findAll()).thenReturn(List.of(home1, home2));

            List<Home> home = (List<Home>) homeRepository.findAll();
            Collection<HomeDto> dtos = homeService.getAllHome();

            assertEquals(home.get(0).getPrice(), dtos.stream().findFirst().get().getPrice());
            assertEquals(home.get(0).getId(), dtos.stream().findFirst().get().getId());
            assertEquals(home.get(0).getType(), dtos.stream().findFirst().get().getType());
            assertEquals(home.get(0).getName(), dtos.stream().findFirst().get().getName());
            assertEquals(home.get(0).getRented(), dtos.stream().findFirst().get().getRented());

        }

        @Test
        void getBooking() {
            Long id = 1L;
            when(homeRepository.findById(id)).thenReturn(Optional.of(home2));

            Home home = homeRepository.findById(id).get();
            HomeDto dto = homeService.getHome(id);

            assertEquals(home.getId(), dto.getId());
            assertEquals(home.getPrice(), dto.getPrice());
            assertEquals(home.getType(), dto.getType());
            assertEquals(home.getName(), dto.getName());
            assertEquals(home.getRented(), dto.getRented());

        }

        @Test
        void updateHomeThrowsExceptionTest() {
            assertThrows(RecordNotFoundException.class, () -> homeService.updateHome(1L,  new HomeDto(1L, "huis", "villa happiness", 1500, "rented")));
        }

        @Test
        void updateHomeThrowsNotFoundExceptionTest() {
            HomeInputDto homeInputDto = new HomeInputDto(1L,"huis","villa happiness", 1500, "rented");
            assertThrows(RecordNotFoundException.class, () -> homeService.updateHome(null,new HomeDto()));
        }

        @Test
        void getHomeThrowsExceptionTest() {
            assertThrows(RecordNotFoundException.class, () -> homeService.getHome(null));
        }

        @Test
        void updateHomeThrowsExceptionForKlantTest() {
            when(homeRepository.findById(any())).thenReturn(Optional.of(home1));
            assertThrows(RecordNotFoundException.class, () -> homeService.updateHome(3L,new HomeDto(1L,"huis","villa happiness", 1500, "rented")));
        }
    }

