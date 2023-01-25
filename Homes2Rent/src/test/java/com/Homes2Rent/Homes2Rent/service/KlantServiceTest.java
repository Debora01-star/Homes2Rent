package com.Homes2Rent.Homes2Rent.service;

import com.Homes2Rent.Homes2Rent.dto.KlantDto;
import com.Homes2Rent.Homes2Rent.dto.KlantInputDto;
import com.Homes2Rent.Homes2Rent.exceptions.DuplicatedEntryException;
import com.Homes2Rent.Homes2Rent.exceptions.RecordNotFoundException;
import com.Homes2Rent.Homes2Rent.model.Klant;
import com.Homes2Rent.Homes2Rent.repository.KlantRepository;

import java.util.HashSet;

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

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ContextConfiguration(classes = {KlantService.class})
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class KlantServiceTest {

    @Mock
    KlantRepository klantRepository;

    @InjectMocks
    KlantService klantService;

    @Captor
    ArgumentCaptor<Klant> argumentCaptor;

    Klant klant1;
    Klant klant2;

    /**
     * Method under test: {@link KlantService#addKlant(KlantInputDto)}
     */
    @Test
    void testAddKlant() throws DuplicatedEntryException {
        Klant klant = new Klant();
        klant.setAddress("42 Main St");
        klant.setCity("Oxford");
        klant.setEmail("jane.doe@example.org");
        klant.setFirstname("Jane");
        klant.setId(123L);
        klant.setLastname("Doe");
        klant.setWoningen(new HashSet<>());
        klant.setZipcode("21654");
        when(klantRepository.existsByEmail((String) any())).thenReturn(true);
        when(klantRepository.save((Klant) any())).thenReturn(klant);
        assertThrows(DuplicatedEntryException.class, () -> klantService.addKlant(new KlantInputDto()));
        verify(klantRepository).existsByEmail((String) any());
    }

    /**
     * Method under test: {@link KlantService#addKlant(KlantInputDto)}
     */
    @Test
    void testAddKlant2() throws DuplicatedEntryException {
        Klant klant = new Klant();
        klant.setAddress("42 Main St");
        klant.setCity("Oxford");
        klant.setEmail("jane.doe@example.org");
        klant.setFirstname("Jane");
        klant.setId(123L);
        klant.setLastname("Doe");
        klant.setWoningen(new HashSet<>());
        klant.setZipcode("21654");
        when(klantRepository.existsByEmail((String) any())).thenReturn(false);
        when(klantRepository.save((Klant) any())).thenReturn(klant);
        KlantDto actualAddKlantResult = klantService.addKlant(new KlantInputDto());
        assertNull(actualAddKlantResult.getAddress());
        assertNull(actualAddKlantResult.getZipcode());
        assertNull(actualAddKlantResult.getWoningen());
        assertNull(actualAddKlantResult.getLastname());
        assertNull(actualAddKlantResult.getFirstname());
        assertNull(actualAddKlantResult.getEmail());
        assertNull(actualAddKlantResult.getCity());
        verify(klantRepository).existsByEmail((String) any());
        verify(klantRepository).save((Klant) any());
    }

    /**
     * Method under test: {@link KlantService#addKlant(KlantInputDto)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAddKlant3() throws DuplicatedEntryException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.Homes2Rent.Homes2Rent.dto.KlantInputDto.getEmail()" because "dto" is null
        //       at com.Homes2Rent.Homes2Rent.service.KlantService.addKlant(KlantService.java:25)
        //   See https://diff.blue/R013 to resolve this issue.

        Klant klant = new Klant();
        klant.setAddress("42 Main St");
        klant.setCity("Oxford");
        klant.setEmail("jane.doe@example.org");
        klant.setFirstname("Jane");
        klant.setId(123L);
        klant.setLastname("Doe");
        klant.setWoningen(new HashSet<>());
        klant.setZipcode("21654");
        when(klantRepository.existsByEmail((String) any())).thenReturn(true);
        when(klantRepository.save((Klant) any())).thenReturn(klant);
        klantService.addKlant(null);
    }

    /**
     * Method under test: {@link KlantService#addKlant(KlantInputDto)}
     */
    @Test
    void testAddKlant4() throws DuplicatedEntryException {
        when(klantRepository.existsByEmail((String) any()))
                .thenThrow(new RecordNotFoundException("Geen factuur gevonden"));
        when(klantRepository.save((Klant) any())).thenThrow(new RecordNotFoundException("Geen factuur gevonden"));
        assertThrows(RecordNotFoundException.class, () -> klantService.addKlant(new KlantInputDto()));
        verify(klantRepository).existsByEmail((String) any());
    }

    @BeforeEach
    void setUp() {
        klant1 = new Klant(1L, "test@email.nl", "testnaam", "gebruiker", "teststraat 77", "teststad", "1234AB");
        klant2 = new Klant(1L, "test@email2.nl", "testnaam2", "gebruiker2", "teststraat 88", "teststad2", "1234CD");
    }

    @Test
    void createKlant() throws DuplicatedEntryException {
        KlantInputDto dto = new KlantInputDto(1L, "test@email.nl", "testnaam", "gebruiker", "teststraat 77", "teststad", "1234AB");

        when(klantRepository.save(klant1)).thenReturn(klant1);

        klantService.addKlant(dto);
        verify(klantRepository, times(1)).save(argumentCaptor.capture());
        Klant klant = argumentCaptor.getValue();

        assertEquals(klant1.getEmail(), klant.getEmail());
        assertEquals(klant1.getFirstname(), klant.getFirstname());
        assertEquals(klant1.getLastname(), klant.getLastname());
        assertEquals(klant1.getAddress(), klant.getAddress());
        assertEquals(klant1.getCity(), klant.getCity());
        assertEquals(klant1.getZipcode(), klant.getZipcode());
    }

    @Test
    void updateKlant() throws DuplicatedEntryException {
        when(klantRepository.findById(1L)).thenReturn(Optional.of(klant1));

        KlantInputDto dto = new KlantInputDto(1L, "test@email.nl", "testnaam", "gebruiker", "teststraat 77", "teststad", "1234AB");

        when(klantRepository.save(klantService.transferToKlant(dto))).thenReturn(klant1);

        klantService.updateKlant(1L, dto);

        verify(klantRepository, times(1)).save(argumentCaptor.capture());

        Klant captured = argumentCaptor.getValue();

        assertEquals(dto.getEmail(), captured.getEmail());
        assertEquals(dto.getFirstname(), captured.getFirstname());
        assertEquals(dto.getLastname(), captured.getLastname());
        assertEquals(dto.getAddress(), captured.getAddress());
        assertEquals(dto.getCity(), captured.getCity());
        assertEquals(dto.getZipcode(), captured.getZipcode());
    }

    @Test
    void deleteKlant() {
        klantService.deleteKlant(1L);

        verify(klantRepository).deleteById(1L);
    }

    @Test
    void getAllKlanten() {
        when(klantRepository.findAll()).thenReturn(List.of(klant1, klant2));

        List<Klant> klanten = (List<Klant>) klantRepository.findAll();
        List<KlantDto> dtos = klantRepository.getAllKlanten();

        assertEquals(klanten.get(0).getEmail(), dtos.get(0).getEmail());
        assertEquals(klanten.get(0).getFirstname(), dtos.get(0).getFirstname());
        assertEquals(klanten.get(0).getLastname(), dtos.get(0).getLastname());
        assertEquals(klanten.get(0).getAddress(), dtos.get(0).getAddress());
        assertEquals(klanten.get(0).getCity(), dtos.get(0).getCity());
        assertEquals(klanten.get(0).getZipcode(), dtos.get(0).getZipcode());
    }

    @Test
    void getKlant() {
        Long id = 1L;
        when(klantRepository.findById(id)).thenReturn(Optional.of(klant2));

        Klant klant = klantRepository.findById(id).get();
        KlantDto dto = klantService.getKlant(id);

        assertEquals(klant.getEmail(), dto.getEmail());
        assertEquals(klant.getFirstname(), dto.getFirstname());
        assertEquals(klant.getLastname(), dto.getLastname());
        assertEquals(klant.getAddress(), dto.getAddress());
        assertEquals(klant.getCity(), dto.getCity());
        assertEquals(klant.getZipcode(), dto.getZipcode());
    }

    @Test
    void updateAppointmentThrowsExceptionTest() {
        assertThrows(RecordNotFoundException.class, () -> klantService.updateKlant(1L, new KlantInputDto(1L, "test@email.nl", "testnaam", "gebruiker", "teststraat 77", "teststad", "1234AB")));
    }

    @Test
    void getAppointmentThrowsExceptionTest() {
        assertThrows(RecordNotFoundException.class, () -> klantService.getKlant(null));
    }
}


